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

                System.out.println("Для Вас доступны направления для хода:" + "\n" + "u" + "\n" + "d" + "\n" + "r" + "\n" + "l");
                String direction = scanner.nextLine();
                // Отправка направления хода
                JSONObject directionCommand = new JSONObject();
                directionCommand.put("command", "direction");
                directionCommand.put("direction", direction);
                out.println(directionCommand.toString());

                response = in.readLine();
                jsonResponse = new JSONObject(response);

                if (jsonResponse.getString("status").equals("go")) {
                    int result = jsonResponse.getInt("result");
                    if (result == 0) {
                        System.out.println("Проход есть, игрок перемещается.");
                    } else if (result == 1) {
                        System.out.println("Стена, игрок остается на месте.");
                    }
                } else if (jsonResponse.getString("status").equals("stop")) {
                    int steps = Integer.parseInt(jsonResponse.getString("result"));
                    int minSteps = Integer.parseInt(jsonResponse.getString("min"));
                    System.out.println("Игрок достиг выхода.");
                    System.out.println("Количество сделанных шагов: " + steps);
                    System.out.println("Минимально возможное число шагов: " + minSteps);
                    JSONArray rating = jsonResponse.getJSONArray("raiting");
                    System.out.println("Рейтинг игроков:");
                    for (int i = 0; i < rating.length(); i++) {
                        JSONObject player = rating.getJSONObject(i);
                        String name = player.getString("name");
                        int playerSteps = Integer.parseInt(player.getString("steps"));
                        int playerMinSteps = Integer.parseInt(player.getString("min"));
                        System.out.println(name + " - Шагов: " + playerSteps + ", Мин. шагов: " + playerMinSteps);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

