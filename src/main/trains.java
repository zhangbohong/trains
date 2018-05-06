package main;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class trains {

    public static void main(String[] args) {
        String start;
        String end;
        TrainDistanceImpl tdi = new TrainDistanceImpl();
        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.println("请选择您的要求：\n\t" +
                    "若要根据行程得出距离，请输入1：\n\t" +
                    "若要根据始末点和最多站数得出行程方案，请输入2：\n\t" +
                    "若要根据始末点得出最短距离，请输入3：\n\t" +
                    "若要根据起始点和最少距离得出对应行程方案，请输入4：");
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
                    Map<String, Integer> routeByStops = tdi.getRouteByStops(start, end, stops);
                    System.out.println("共有" + routeByStops.size() + "种行程方案：");
                    for (Map.Entry<String, Integer> entry : routeByStops.entrySet()) {
                        System.out.print("路径：" + entry.getKey());
                        System.out.println("*******长度：" + entry.getValue());
                    }
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
                    System.out.println("请输入起始站点：");
                    start = in.nextLine();
                    System.out.println("请输入结束站点：");
                    end = in.nextLine();
                    System.out.println("请输入所能接受的最大距离：");
                    int maxDistance = in.nextInt();
                    Map<String, Integer> routeByDistance = tdi.getRouteByDistance(start, end, maxDistance);
                    System.out.println("共有" + routeByDistance.size() + "种行程方案：");
                    for (Map.Entry<String, Integer> entry : routeByDistance.entrySet()) {
                        System.out.print("路径：" + entry.getKey());
                        System.out.println("*******长度：" + entry.getValue());
                    }
                    break;
                default:
                    System.out.println("输入有误，请重新输入！");
            }
        }
    }
}
