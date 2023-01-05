package src;

import java.util.ArrayList;
import java.util.HashMap;

import src.Models.Campeonatos.Campeonato;
import src.Models.Campeonatos.CarroSetup;
import src.Models.Campeonatos.Corrida;
import src.Models.Campeonatos.Registo;
import src.Models.Campeonatos.CarroSetup.ModoMotor;
import src.Models.Campeonatos.CarroSetup.Pneus;
import src.Models.Campeonatos.Corrida.Clima;
import src.Models.Carros.C1;
import src.Models.Circuitos.Circuito;
import src.Models.Circuitos.Circuito.GDU;
import src.Models.Pilotos.Piloto;
import src.Models.Utilizadores.Anonimo;
import src.Models.Utilizadores.Registado;

public class Test {
    public static void main(String[] args) {
        System.out.println("HELLO CARALHO");

        Registado registado = new Registado("Dudas", "dudaspass", 30);

        Piloto piloto = new Piloto("Pilotaço", 0.5, 0.5);

        ArrayList<GDU> retasGDU = new ArrayList<>();
        retasGDU.add(GDU.DIFICIL);
        retasGDU.add(GDU.IMPOSSIVEL);
        retasGDU.add(GDU.POSSIVEL);

        ArrayList<GDU> curvasGDU = new ArrayList<>();
        curvasGDU.add(GDU.DIFICIL);
        curvasGDU.add(GDU.IMPOSSIVEL);
        curvasGDU.add(GDU.POSSIVEL);

        Circuito circuito1 = new Circuito(20, 3, 4, 5, retasGDU, curvasGDU);
        Circuito circuito2 = new Circuito(30, 5, 4, 5, retasGDU, curvasGDU);
        Circuito circuito3 = new Circuito(50, 4, 4, 5, retasGDU, curvasGDU);

        Corrida corrida1 = new Corrida(circuito1, Clima.MOLHADO, new HashMap<>());
        Corrida corrida2 = new Corrida(circuito2, Clima.SECO, new HashMap<>());
        Corrida corrida3 = new Corrida(circuito3, Clima.MOLHADO, new HashMap<>());

        ArrayList<Corrida> corridas = new ArrayList<>();
        corridas.add(corrida1);
        corridas.add(corrida2);
        corridas.add(corrida3);


        CarroSetup carroSetup1 = new CarroSetup(0.5, Pneus.CHUVA, ModoMotor.AGRESSIVO, new C1("marca fixe", "modelo fixolas", 30, 0, 0));
        CarroSetup carroSetup2 = new CarroSetup(0.5, Pneus.CHUVA, ModoMotor.AGRESSIVO, new C1("marca horrivel", "modelo horrivel", 30, 1, 20));
 
        Registo r1 = new Registo(0, piloto, registado, carroSetup1);
        Registo r2 = new Registo(0, piloto, new Anonimo(), carroSetup2);

        HashMap<String, Registo> registos = new HashMap<>();
        registos.put(registado.getNome(), r1);
        registos.put("anonimo", r2);

        Campeonato campeonato = new Campeonato("Cmapeonato fixe", corridas, registos);

        System.out.println("CAMPEONATOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        System.out.println(campeonato);






    }

    
}
