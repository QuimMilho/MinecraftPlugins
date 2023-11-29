package net.quimmilho.mineclient;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Connection {

    private final String pluginDir;
    private Socket socket;

    public Connection(String pluginDir) {
        this.pluginDir = pluginDir;
    }

    public void start() throws IOException {
        Path path = Paths.get(pluginDir);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }

        path = Paths.get(pluginDir + "/server.yml");
        if (!Files.exists(path)) {
            Files.createFile(path);
            createInfo(path);
        }

        Map<String, Object> info = readInfo(path);
        String host = (String) info.get("server.ip");
        int port = (int) info.get("server.port");
        System.out.println(" - " + host + ":" + port);

        socket = new Socket();
        socket.connect(new InetSocketAddress(host, port), 1000);
    }

    public int send(String str) throws IOException {
        byte[] data = str.getBytes(StandardCharsets.US_ASCII);

        OutputStream out = socket.getOutputStream();
        out.write(data);
        out.close();

        return data.length;
    }

    public String recv() throws IOException {
        InputStream in = socket.getInputStream();
        byte[] b = in.readAllBytes();
        in.close();

        return new String(b, StandardCharsets.US_ASCII);
    }

    private Map<String, Object> readInfo(Path path) throws IOException {
        Yaml yaml = new Yaml();
        StringBuilder info = new StringBuilder();
        String temp;

        File f = path.toFile();
        BufferedReader br = new BufferedReader(new FileReader(f));
        while ((temp = br.readLine()) != null) {
            info.append(temp + "\n");
        }

        return yaml.load(info.toString());
    }

    private void createInfo(Path path) throws IOException {
        Map<String, Object> serverInfo = new HashMap<>();
        serverInfo.put("server.ip", "127.0.0.1");
        serverInfo.put("server.port", 3000);

        DumperOptions dumper = new DumperOptions();
        dumper.setIndent(4);
        dumper.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Yaml yaml = new Yaml(dumper);
        String info = yaml.dump(serverInfo);
        System.out.println(info);

        File f = path.toFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        bw.write(info);

        bw.close();
    }

}
