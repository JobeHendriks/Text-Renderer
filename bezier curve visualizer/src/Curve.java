import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class Curve {
    private double[][] bezierCurve;
    private double[] point1,point2,point3,point4;

    public Curve(double[] point1, double[] point2, double[] point3) throws Exception {
        if(point1.length > 2){
            throw new Exception("point1 too large");
        } else if (point2.length > 2) {
            throw new Exception("point2 too large");
        } else if (point3.length > 2) {
            throw new Exception("point3 too large");
        }
        bezierCurve = bezierInterpolation(point1,point2,point3);
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
    }

    public Curve(double[] point1, double[] point2, double[] point3, double[] point4) throws Exception{
        if(point1.length > 2){
            throw new Exception("point1 too large");
        } else if (point2.length > 2) {
            throw new Exception("point2 too large");
        } else if (point3.length > 2) {
            throw new Exception("point3 too large");
        } else if (point4.length > 2) {
            throw new Exception("point4 too large");
        }
        bezierCurve = bezierInterpolation(point1,point2,point3,point4);
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.point4 = point4;
    }

    public void draw(Graphics2D graphics2D,Color color){
        graphics2D.setColor(color);
        for (int i = 0; i < bezierCurve.length - 1; i++){
            graphics2D.draw(new Line2D.Double(bezierCurve[i][0],bezierCurve[i][1],bezierCurve[i+1][0],bezierCurve[i+1][1]));
        }

    }

    public void drawPoints(Graphics2D graphics2D){
        graphics2D.setColor(Color.white);
        graphics2D.draw(new Line2D.Double(point1[0],point1[1],point2[0],point2[1]));
        graphics2D.draw(new Line2D.Double(point2[0],point2[1],point3[0],point3[1]));
        double[] tempPoint1 = linearInterpolation(point1,point2,0.5);
        double[] tempPoint2 = linearInterpolation(point2,point3,0.5);
        if (point4 != null){
         graphics2D.draw(new Line2D.Double(point3[0],point3[1],point4[0],point4[1]));

            double[] tempPoint3 = linearInterpolation(point3,point4,0.5);
            double[] secondaryPoint1 = linearInterpolation(tempPoint1,tempPoint2,0.5);
            double[] secondaryPoint2 = linearInterpolation(tempPoint2,tempPoint3,0.5);

            graphics2D.setColor(Color.red);
            graphics2D.draw(new Line2D.Double(tempPoint2[0],tempPoint2[1],tempPoint3[0],tempPoint3[1]));
            graphics2D.setColor(Color.green);
            graphics2D.draw(new Line2D.Double(secondaryPoint1[0],secondaryPoint1[1],secondaryPoint2[0],secondaryPoint2[1]));
        }
        graphics2D.setColor(Color.cyan);
        graphics2D.draw(new Line2D.Double(tempPoint1[0],tempPoint1[1],tempPoint2[0],tempPoint2[1]));
    }
    private double[]linearInterpolation(double[] point1,double[] point2, double time){
        double x0,y0;
        x0 = point1[0] + ((point2[0]-point1[0]) * time);
        if (point1[0] == point2[0]){
            y0 = point1[1] + ((point2[1]-point1[1]) * time);
        }else {
            y0 = point1[1] + (x0 - point1[0]) * ((point2[1] - point1[1]) / (point2[0] - point1[0]));
        }
        return new double[]{x0, y0};
    }

    private double[][]bezierInterpolation(double[] point1, double[] point2, double[] point3){
        double[][] subPoints =  new double[Const.BEZIER_RESOLUTION + 2][2];
        double curveResolution = 0;
        for (int i  = 0; i < subPoints.length - 1; i++){
            double[] tempPoint1 = linearInterpolation(point1,point2,curveResolution);
            double[] tempPoint2 = linearInterpolation(point2,point3, curveResolution);
            subPoints[i] = linearInterpolation(tempPoint1,tempPoint2,curveResolution);
            curveResolution += (double) 1/ Const.BEZIER_RESOLUTION;
        }
        subPoints[subPoints.length - 1] = point3;
        return subPoints;
    }

    private double[][]bezierInterpolation(double[] point1, double[] point2, double[] point3, double[] point4){
        double[][] subPoints = new double[Const.BEZIER_RESOLUTION][2];
        double curveResolution = 0;
        for (int i = 0; i < subPoints.length-1; i++){
            double[] tempPoint1 = linearInterpolation(point1,point2,curveResolution);
            double[] tempPoint2 = linearInterpolation(point2,point3,curveResolution);
            double[] tempPoint3 = linearInterpolation(point3,point4,curveResolution);
            double[] secondaryPoint1 = linearInterpolation(tempPoint1,tempPoint2,curveResolution);
            double[] secondaryPoint2 = linearInterpolation(tempPoint2,tempPoint3,curveResolution);
            subPoints[i] = linearInterpolation(secondaryPoint1,secondaryPoint2,curveResolution);
            curveResolution += (double) 1/ Const.BEZIER_RESOLUTION;
        }
        subPoints[subPoints.length - 1] = point4;
        return subPoints;
    }

    public void bezierTest(double[] point1, double[] point2, double[] point3, double[] point4, double time, Graphics2D graphics2D){
        double[] tempPoint1 = linearInterpolation(point1,point2,time);
        double[] tempPoint2 = linearInterpolation(point2,point3,time);
        double[] tempPoint3 = linearInterpolation(point3,point4,time);
        double[] secondaryPoint1 = linearInterpolation(tempPoint1,tempPoint2,time);
        double[] secondaryPoint2 = linearInterpolation(tempPoint2,tempPoint3,time);
        graphics2D.draw(new Line2D.Double(tempPoint1[0],tempPoint1[1],tempPoint2[0],tempPoint2[1]));
        graphics2D.setColor(Color.red);
        graphics2D.draw(new Line2D.Double(tempPoint2[0],tempPoint2[1],tempPoint3[0],tempPoint3[1]));
        graphics2D.setColor(Color.green);
        graphics2D.draw(new Line2D.Double(secondaryPoint1[0],secondaryPoint1[1],secondaryPoint2[0],secondaryPoint2[1]));
    }

    public void fillCurve(Graphics2D graphics2D){

    }
}

