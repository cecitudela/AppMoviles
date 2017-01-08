package com.example.cecilia.appmoviles;

import android.content.Context;

import com.example.cecilia.appmoviles.sqlite.Beach;
import com.example.cecilia.appmoviles.sqlite.BeachDataSource;

import java.util.List;

/**
 * Created by Cecilia on 27/12/2016.
 */

public class FiltersBeachs {

    private BeachDataSource beachSource;
    public FiltersBeachs(Context context){
        beachSource = new BeachDataSource(context);
    }


    public List<Beach> getBeachsByName(String name, int seleccionSpinner){
        beachSource.open();
        List<Beach> list = null;
        switch(seleccionSpinner){
            case 0: //Todas
                list = beachSource.getBeachByName(name);
                break;
            case 1: //Zona surf
                list = beachSource.getBeachByNameAndZonaSurf(name);
                break;
            case 2: //Duchas
                list = beachSource.getBeachByNameAndDuchas(name);
                break;
            case 3: //Establecimiento comida
                list = beachSource.getBeachByNameAndEstComida(name);
                break;
            case 4: //Alquiler hamacas
                list = beachSource.getBeachByNameAndAlquHam(name);
                break;
            case 5: //Nudista
                list = beachSource.getBeachByNameAndNudista(name);
                break;
            case 6: //Serv limpieza
                list = beachSource.getBeachByNameAndServLimp(name);
                break;
            case 7: //Oficina de turismo
                list = beachSource.getBeachByNameAndOfTur(name);
                break;


        }
        beachSource.close();
        return list;
    }

}
