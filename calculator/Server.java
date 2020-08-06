package calculator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static ServerSocket server;
    private static int port = 9876;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        server = new ServerSocket(port);
        Socket socket = server.accept();
        while (true) {
            System.out.println("Waiting for client request");
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Long startTimer = System.currentTimeMillis();
            String message = (String) ois.readObject();
            if ("exit".equals(message)) {
                ois.close();
                oos.close();
                socket.close();
                break;
            }
            else {
                String result = calculate(message);
                Long finishTimer = System.currentTimeMillis();
                long calculationTime = (finishTimer - startTimer)/1000;
                oos.writeObject("$" + Long.toString(calculationTime) + "$" + result + "$");
            }
        }

        System.out.println("Shutting down Socket server!!");
        server.close();

    }

    private static String calculate(String message) {
        String[] item = message.split("\\$");
        String operator = item[1];
        String op1 = item[2];
        String result = "";
        if ("Add".equals(operator)) {
            String op2 = item[3];
            result = Float.toString(Float.parseFloat(op1) + Float.parseFloat(op2));
        }
        else if ("Subtract".equals(operator)){
            String op2 = item[3];
            result = Float.toString(Float.parseFloat(op1) - Float.parseFloat(op2));
        }
        else if ("Divide".equals(operator)){
            String op2 = item[3];
            result = Float.toString(Float.parseFloat(op1) / Float.parseFloat(op2));
        }
        else if ("Multiply".equals(operator)){
            String op2 = item[3];
            result = Float.toString(Float.parseFloat(op1) * Float.parseFloat(op2));
        }
        else if ("Sin".equals(operator)){
            double op1Radian = Math.toRadians(Integer.parseInt(op1));
            result = Double.toString(Math.sin(op1Radian));
        }
        else if ("Cos".equals(operator)){
            double op1Radian = Math.toRadians(Integer.parseInt(op1));
            result = Double.toString(Math.cos(op1Radian));
        }
        else if ("Tan".equals(operator)){
            double op1Radian = Math.toRadians(Integer.parseInt(op1));
            result = Double.toString(Math.tan(op1Radian));
        }
        else if ("Cot".equals(operator)){
            double op1Radian = Math.toRadians(Integer.parseInt(op1));
            result = Double.toString(1/(Math.tan(op1Radian)));
        } else
            result = "The requested operation is not defined!";
        return result;
    }
}
