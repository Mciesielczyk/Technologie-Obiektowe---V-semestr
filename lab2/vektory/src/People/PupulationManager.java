package People;
import States.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PupulationManager {
    private List<Person> population = new ArrayList<>();
    public double x,y;
    public double time;
    public double stepTime=0.04;
    private double newPersonFreq;
    public double maxSpeed = 2.5;
    long startTime = System.currentTimeMillis();
    double dt=0.04;
    private Random rand;
    int ilosc = 150;





    public PupulationManager(double x,double y){
        this.x=x;
        this.y=y;
        rand=new Random();
    }


    public void run() throws InterruptedException {
        startTime = System.currentTimeMillis();
        populate(ilosc);

        JFrame frame = new JFrame("Symulacja");
        MapPanel panel = new MapPanel(population,x,y);
        frame.add(panel);


        // Panel przycisków na dole
        JPanel buttonPanel = new JPanel();

        // Przycisk do zapisu stanu
        JButton saveButton = new JButton("Zapisz stan");
        saveButton.addActionListener(e -> saveSimulation());
        buttonPanel.add(saveButton);

        // Przycisk do wczytania stanu
        JButton loadButton = new JButton("Wczytaj stan");
        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                loadSimulation(selectedFile, panel); // przekaż panel
            }
        });

        buttonPanel.add(loadButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        while(true){
            step();
            Thread.sleep((long)(dt * 1000)); //
            panel.repaint();
              time=time+dt;
        }
    }


    public void step(){

        for (Person p : new ArrayList<>(population)) {
            p.move(stepTime);
           // p.reflectOrExit(x,y);
            p.getPosition();
        }

        for(Person p:population){
            p.getState().update(p,stepTime);
        }

        for (int i = 0; i < population.size(); i++) {
            for (int j = i + 1; j < population.size(); j++) {
                Person a = population.get(i);
                Person b = population.get(j);
                a.getState().onContact(a, b, stepTime);
                b.getState().onContact(b, a, stepTime);
            }
        }
        addNewPeopleR();

    }

    private void addNewPeople(){
        Person person = createP();
        population.add(person);
    }
    private void addNewPeopleR(){
        Person person = createPR();
        population.add(person);
    }

    private Person createP(){
        Random rand = new Random();
        double x1 = rand.nextDouble(x);
        double y1 = rand.nextDouble(y);
        Person person = new Person(x1,y1,this);

        return person;
    }

    public void subtract(Person person){
        population.remove(person);
    }

    private Person createPR() {
        Random rand = new Random();

        double x1 = 0;
        double y1 = 0;

        // Wylosuj jedną z 4 granic (0=lewa, 1=prawa, 2=dolna, 3=górna)
        int edge = rand.nextInt(4);

        switch (edge) {
            case 0:
                x1 = 0;
                y1 = rand.nextDouble(y);
                break;
            case 1:
                x1 = x - 0.0001; // prawa
                y1 = rand.nextDouble(y);
                break;
            case 2:
                x1 = rand.nextDouble(x);
                y1 = 0;
                break;
            case 3:
                x1 = rand.nextDouble(x);
                y1 = y - 0.0001; // górna
                break;
        }


        Person person = new Person(x1, y1, this);
        return person;
    }



    public double getTime(){
        return time;
    }

    private void populate(int qua){
        for(int j=0;j<qua;j++){
            addNewPeople();
        }
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public void saveSimulation() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Wybierz plik do zapisu stanu");
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileToSave))) {
                oos.writeObject(population); // zapisujemy listę osobników
                System.out.println("Stan symulacji zapisany do: " + fileToSave.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // ---------------- Wczytanie -----------------
    @SuppressWarnings("unchecked")
    public void loadSimulation(File file, MapPanel panel) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<Person> loadedPopulation = (List<Person>) ois.readObject();
            population.clear();                // Wyczyść starą listę
            population.addAll(loadedPopulation); // Dodaj wczytane osoby

            // Ustaw manager dla każdej osoby
            for (Person p : population) {
                p.setPopulationManager(this);
            }

            // Zaktualizuj panel
            panel.repaint();

            System.out.println("Stan symulacji wczytany z: " + file.getAbsolutePath());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            //population.clear();                // Wyczyść starą listę

        }
    }



}
