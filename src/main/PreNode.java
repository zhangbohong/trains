package main;

public class PreNode {

    private String preNodeNum;// 最优 前一个节点
    private int nodeStep;// 最小步长

    public PreNode(String preNodeNum, int nodeStep) {
        this.preNodeNum = preNodeNum;
        this.nodeStep = nodeStep;
    }

    public String getPreNodeNum() {
        return preNodeNum;
    }
    public void setPreNodeNum(String preNodeNum) {
        this.preNodeNum = preNodeNum;
    }
    public int getNodeStep() {
        return nodeStep;
    }
    public void setNodeStep(int nodeStep) {
        this.nodeStep = nodeStep;
    }

}
