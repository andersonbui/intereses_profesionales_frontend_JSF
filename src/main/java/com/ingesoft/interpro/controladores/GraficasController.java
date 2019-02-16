package com.ingesoft.interpro.controladores;

import be.ceau.chart.BarChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
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

        List datas = new ArrayList();
        datas.add(12.0);
        datas.add(19.0);
        datas.add(3.0);
        datas.add(5.0);
        datas.add(2.0);
        datas.add(32.0);
        List<Color> colors = new ArrayList();
        colors.add(new Color(255, 0, 0, 0.7));
        colors.add(new Color(0, 0, 255, 0.7));
        colors.add(new Color(255, 170, 0, 0.7));
        colors.add(new Color(0, 255, 255, 0.7));
        colors.add(new Color(255, 255, 0, 0.7));
        colors.add(new Color(0, 255, 0, 0.7));

        BarDataset dataset = new BarDataset()
                .setLabel("sample chart")
                .setData(datas)
                .setBackgroundColor(colors)
                .setBorderWidth(0);
//        dataset.s
        BarData data = new BarData();
        data.setLabels("Rojo", "Azul", "Wednesday", "Thursday", "Friday", "Saturday")
                .addDataset(dataset);

        return new BarChart(data).toJson();
    }

}
