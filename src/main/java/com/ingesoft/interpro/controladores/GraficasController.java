package com.ingesoft.interpro.controladores;

import be.ceau.chart.BarChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Line;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "graficasController")
@SessionScoped
public class GraficasController implements Serializable {

    public String datos;

    public GraficasController() {

    }

//    public String getDatos() {
////        com.github.abel533.echarts.series.Bar option;
////        option.setLabel("hola");
//        GsonOption option = new GsonOption();
//        option.legend("高度(km)与气温(°C)变化关系");
//
//        option.toolbox().show(true).feature(
//                Tool.mark,
//                Tool.dataView,
//                new MagicType( Magic.bar),
//                Tool.restore,
//                Tool.saveAsImage);
//
//        option.calculable(true);
//        option.tooltip().trigger(Trigger.axis).formatter("Temperature : <br/>{b}km : {c}°C");
//
//        ValueAxis valueAxis = new ValueAxis();
//        valueAxis.axisLabel().formatter("{value} °C");
//        option.xAxis(valueAxis);
// 
//        CategoryAxis categoryAxis = new CategoryAxis();
//        categoryAxis.axisLine().onZero(false);
//        categoryAxis.axisLabel().formatter("{value} km");
//        categoryAxis.boundaryGap(false);
//        categoryAxis.data(0, 10, 20, 30, 40, 50, 60, 70, 80);
//        option.yAxis(categoryAxis);
//
//        Line line = new Line();
//        line.smooth(true).name("este es un nombre")
//                .data(15, -50, -56.5, -46.5, -22.1, -2.5, -27.7, -55.7, -76.5)
//                .itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");
//        option.series(line);
//        System.out.println("option"+option.toString());
////        option.exportToHtml("line5.html");
////        option.view();
//
//        com.github.abel533.echarts.code.Tool chr;
////        chr.
//        return option.toPrettyString();
//    }

    public String getDatos() {
        
        List datas = new ArrayList();
        datas.add(12.0);
        datas.add(19.0);
        datas.add(3.0);
        datas.add(5.0);
        datas.add(2.0);
        datas.add(32.0);
        datas.add(10.0);
        
        BarDataset dataset = new BarDataset()
		.setLabel("sample chart")
		.setData(datas)
		.addBackgroundColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.GRAY, Color.BLACK)
		.setBorderWidth(0);
//        dataset.s
        BarData data = new BarData();
        data.setLabels("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
                    .addDataset(dataset);

        return new BarChart(data).toJson();
    }
    
    public String getDatos2() {

        BarChartModel barModel = new BarChartModel();
        barModel.data = new ChartData();
        barModel.type = "bar";
        barModel.data.datasets = new ArrayList<>();
        barModel.data.labels = new ArrayList<>();

        barModel.data.labels.add("rojo");
        barModel.data.labels.add("azul");
        barModel.data.labels.add("naranja");
        barModel.data.labels.add("azul_claro");
        barModel.data.labels.add("amarillo");
        barModel.data.labels.add("verde");

        Dataset dataset = new Dataset();
        barModel.data.datasets.add(dataset);
        dataset.label = "Votos:";
        dataset.data = new ArrayList();
        dataset.data.add(12.0);
        dataset.data.add(19.0);
        dataset.data.add(3.0);
        dataset.data.add(5.0);
        dataset.data.add(2.0);
        dataset.data.add(32.0);
        dataset.backgroundColor = new ArrayList();
        dataset.backgroundColor.add("rgba(255,0,0,0.6)");
        dataset.backgroundColor.add("rgba(0,0,255,0.6)");
        dataset.backgroundColor.add("#FFD42A");
        dataset.backgroundColor.add("rgba(0,255,255,0.6)");
        dataset.backgroundColor.add("rgba(255,255,0,0.6)");
        dataset.backgroundColor.add("#008000");

        GsonBuilder gbuilder = new GsonBuilder();
        Gson gson = gbuilder.create();
        System.out.println("transformado" + gson.toJson(gson));
        return gson.toJson(barModel);
    }

    public class BarChartModel {

        String type;
        ChartData data;
        Options options;

        public BarChartModel() {
//            options = new Options();
        }
    }

    public class ChartData {

        List<String> labels;
        List<Dataset> datasets;
    }

    public class Dataset {

        String label;
        List<Double> data;
        List<String> backgroundColor;
        List<String> borderColor;
        double borderWidth;
    }

    public class Options {

        /**
         * ["mousemove", "mouseout", "click", "touchstart", "touchmove",
         * "touchend"]
         */
        List<String> events;
        Tooltip tooltips;
        Animation animation;

        public Options() {
            events = new ArrayList<>();
            events.add("touchmove");
            tooltips = new Tooltip();
            animation = new Animation();
        }

    }

    public class Tooltip {

        String mode;
        String axis;

        public Tooltip() {
            mode = "nearest";
            axis = "";
        }

    }

    public class Animation {

        String onProgress;

        public Animation() {
//            onProgress = "function(animation) {" +
//"                progress.value = animation.animationObject.currentStep / animation.animationObject.numSteps;" +
//"            }";
        }

    }
}
