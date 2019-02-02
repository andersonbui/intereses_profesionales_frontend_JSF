package com.ingesoft.interpro.controladores;

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

    public String getDatos() {
        BarChartModel barModel = new BarChartModel();
        barModel.data = new ChartData();
        barModel.type = "pie";
        barModel.data.datasets = new ArrayList<>();
        barModel.data.labels = new ArrayList<>();
        
        barModel.data.labels.add("rojo");
        barModel.data.labels.add("azul");
        barModel.data.labels.add("amarillo");
        barModel.data.labels.add("naranja");
        barModel.data.labels.add("purpura");
        barModel.data.labels.add("verde");
        
        Dataset dataset = new Dataset();
        barModel.data.datasets.add(dataset);
        dataset.label="Votos:";
        dataset.data = new ArrayList();
        dataset.data.add(12.0);
        dataset.data.add(19.0);
        dataset.data.add(3.0);
        dataset.data.add(5.0);
        dataset.data.add(2.0);
        dataset.data.add(32.0);
        dataset.backgroundColor = new ArrayList();
        dataset.backgroundColor.add("#FF0000");
        dataset.backgroundColor.add("#0000FF");
        dataset.backgroundColor.add("#FFD42A");
        dataset.backgroundColor.add("#E66300");
        dataset.backgroundColor.add("#FFFF00");
        dataset.backgroundColor.add("#008000");
        
        GsonBuilder gbuilder = new GsonBuilder();
        Gson gson = gbuilder.create();
        System.out.println("transformado"+gson.toJson(gson));
        return gson.toJson(barModel);
    }
    
    public class BarChartModel{
        String type;
        ChartData data;
        Option options;
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
    public class Option {
        
    }
}
