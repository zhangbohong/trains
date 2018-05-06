package main;

import java.util.List;
import java.util.Scanner;

public class trains {

    public static void main(String[] args) {
        String start;
        String end;
        TrainDistanceImpl tdi = new TrainDistanceImpl();
        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.println("请选择您的要求：\n\t" +
                    "1、输入行程，输出距离；\n\t" +
                    "2、输入始末点和最多站数，输出行程方案；\n\t" +
                    "3、输入始末点，输出最短距离；\n\t" +
                    "4、输入起始点和最少距离，输出对应行程方案；");
            String type = in.nextLine();
            switch (type) {
                case "1":
                    System.out.println("请输入行程：");
                    String route = in.nextLine();
                    int distance = tdi.getDistanceByRoute(route);
                    System.out.println("该行程的距离为：" + (distance == -1 ? "没有该路径" : distance));
                    break;
                case "2":
                    System.out.println("请输入起始站点：");
                    start = in.nextLine();
                    System.out.println("请输入结束站点：");
                    end = in.nextLine();
                    System.out.println("请输入最多站数：");
                    int stops = in.nextInt();
                    MinStep routeByDistance = tdi.getRouteByStops(start, end, stops);
                    break;
                case "3":
                    System.out.println("请输入起始站点：");
                    start = in.nextLine();
                    System.out.println("请输入结束站点：");
                    end = in.nextLine();
                    MinStep minStep = tdi.getMinStep(start, end);
                    int minDistance = minStep.getMinStep();
                    List<String> step = minStep.getMinRoute();
                    System.out.println("该始末点的最短距离为：" + (minDistance == -1 ? "没有该路径！" : minDistance));
                    System.out.println("该始末点的最短路径为：" + (step == null ? "没有该路径!" : step));
                    break;
                case "4":
                    break;
                default:
                    System.out.println("输入有误，请重新输入！");
            }
        }
    }
}
