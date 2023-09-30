package mobi.foo.com.inpt.cyberrange.services;

import mobi.foo.com.inpt.cyberrange.domain.Exercise;
import mobi.foo.com.inpt.cyberrange.domain.Stack;
import mobi.foo.com.inpt.cyberrange.domain.Status;
import mobi.foo.com.inpt.cyberrange.domain.AppUser;
import mobi.foo.com.inpt.cyberrange.repository.StackRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MachineService {

    @PostConstruct
    public void setup() {
//        System.out.println(new Date());
//        System.out.println(available(8081));
//        System.out.println(new Date());
    }

    private final StackRepository stackRepository;

    public Stack run(Exercise exercise, AppUser user) {
        Stack stack = new Stack()
                .setExercise(exercise)
                .setStarted(new Date())
                .setStatus(Status.CREATED)
                .setScore(0)
                .setUser(user);
        stackRepository.save(stack);
        String file = generateFile(readFile(exercise.getScript()), stack);
        stack.setFile(file);
        boolean success = runScript(file, stack);
        if (success) {
            return stackRepository.save(stack);
        } else {
            return null;
        }
    }

    @SneakyThrows
    public String generateFile(String script, Stack stack) {
        script = updateScript(script, stack);
        String fileName = UUID.randomUUID().toString() + ".yml";
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        writer.print(script);
        writer.close();
        return fileName;
    }

    private String updateScript(String script, Stack stack) {
        Integer port = getPort();
        stack.setPort(port);
        stackRepository.save(stack);
        if (script.contains("{ { port } }")) {
            script = script.replace("{ { port } }", port + "");
        }
        return script;
    }

    private boolean available(int port) {
        try (Socket ignored = new Socket("localhost", port)) {
            return false;
        } catch (IOException ignored) {
            return true;
        }
    }

    private int getRandomPort() {
        Random r = new Random();
        int low = 10000;
        int high = 65536;
        int result = r.nextInt(high - low) + low;
        return result;
    }

    private int getPort() {
        int port = getRandomPort();
        while (!available(port)) {
        }
        return port;
    }

    public boolean runScript(String fileName, Stack stack) {
        String stackName = UUID.randomUUID().toString();
        stack.setName(stackName);
        File file = new File(fileName);
        Process process;
        try {
            process = new ProcessBuilder("docker", "stack", "deploy", "-c", file.getAbsolutePath(), stackName).start();
            process.waitFor(1000, TimeUnit.SECONDS);
            if (process.exitValue() == 0) {
                // Success
                stack.setLog(printProcessOutput(process.getInputStream()));
                stack.setStatus(Status.RUNNING);
                stackRepository.save(stack);
                return true;
            } else {
                stack.setLog(printProcessOutput(process.getErrorStream()));
                stack.setStatus(Status.FAILED);
                stackRepository.save(stack);
                return false;
            }
        } catch (Exception e) {
            stack.setStatus(Status.FAILED);
            stackRepository.save(stack);
            e.printStackTrace();
            return false;
        }
    }

    public boolean close(Stack stack) {
        Process process;
        try {
            process = new ProcessBuilder("docker", "stack", "rm", stack.getName()).start();
            process.waitFor(100, TimeUnit.SECONDS);
            if (process.exitValue() == 0) {
                // Success
                stack.setLog(stack.getLog() + "\n" + printProcessOutput(process.getInputStream()));
                stack.setStatus(Status.CLOSED);
                stackRepository.save(stack);
                return true;
            } else {
                stack.setLog(stack.getLog() + "\n" + printProcessOutput(process.getErrorStream()));
                stack.setStatus(Status.CLOSE_FAILED);
                stackRepository.save(stack);
                return false;
            }
        } catch (Exception e) {
            stack.setStatus(Status.CLOSE_FAILED);
            stackRepository.save(stack);
            e.printStackTrace();
            return false;
        }
    }

    private String printProcessOutput(InputStream inputStream) throws IOException {
        try (InputStreamReader isr = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(isr)) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();
        }
    }

    @SneakyThrows
    public String readFile(String fileName) {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }
}
