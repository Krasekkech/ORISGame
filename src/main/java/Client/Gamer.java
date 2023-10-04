package Client;

// 1 - connect
// 2 - send start massage
// 3 - концепция цикла
// while(status)
// в цикле процедуры: считать направление(команду) с консоли
// отправка команды, пока не достигнем завершения



import com.github.tsohr.JSONArray;
import com.github.tsohr.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Gamer {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";
        int serverPort = 12345;

        try {

            Scanner scanner = new Scanner(System.in);

            Socket socket = new Socket(serverAddress, serverPort);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.print("Введите ваше имя: ");
            String clientName = scanner.nextLine();

            JSONObject startCommand = new JSONObject();
            startCommand.put("command", "start");
            startCommand.put("clientName", clientName);
            out.println(startCommand.toString());

            String response = in.readLine();
            JSONObject jsonResponse = new JSONObject(response);
            while (response != null) {
                if (jsonResponse.getString("status").equals("start")) {
                    System.out.println(jsonResponse.getString("message"));
                    JSONArray startPoint = jsonResponse.getJSONArray("startPoint");
                    System.out.println("Начальная точка: (" + startPoint.getInt(1) + ", " + startPoint.getInt(1) + ")");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

