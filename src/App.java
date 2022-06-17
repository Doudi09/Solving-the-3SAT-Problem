import BSO.BSO;
import GA.Population;
import PSO.PSO;
import SAT.SAT;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class App extends Application implements Initializable {

    public static void main(String[] args) {
        launch(args);
    }




    public int nbrIterationGA = 1020, mutation = 5, population = 24;
    public double nbrIterationPSO = 1990, particule = 20, c1 = 1.1, c2 = 1.3, w = 0.625;
    public int nbrIterationBSO = 2540, flip = 1, nbrBee = 18, nbrLocalBee = 26, chance = 5;
    public int nbrEssai = 3;

    double t1, t2;
    ArrayList<String> fichier;


    @FXML
    MenuButton comboAlgo;
    @FXML
    HBox paneReglageGA;
    @FXML
    HBox paneReglagePSO;
    @FXML
    HBox paneReglageBSO;
    @FXML
    VBox paneParam;
    @FXML
    Button btnFixer;
    @FXML
    Button btnRun;
    @FXML
    Button btnInitialiser;
    @FXML
    Button btnChooseFile;

    @FXML
    ComboBox comboNbrItGA;
    @FXML
    ComboBox combboPopulation;
    @FXML
    ComboBox comboMutation;

    @FXML
    ComboBox comboNbrItPSO;
    @FXML
    ComboBox comboParticule;
    @FXML
    ComboBox comboC1;
    @FXML
    ComboBox comboC2;
    @FXML
    ComboBox comboW;

    @FXML
    ComboBox comboNbrItBSO;
    @FXML
    ComboBox comboFlip;
    @FXML
    ComboBox comboNbrBee;
    @FXML
    ComboBox comboNbrLocalBee;
    @FXML
    ComboBox comboNbChance;

    @FXML
    ComboBox comboNbrEssai;
    @FXML
    MenuButton comboFile;

    @FXML
    VBox paneResult;
    @FXML
    TextFlow txtResult;
    @FXML
    VBox paneGraph;

    @FXML
    BarChart barchartTemps;
    @FXML
    BarChart barchartSat;

    @FXML
    NumberAxis yTauxSat;
    @FXML
    CategoryAxis xSat;
    @FXML
    NumberAxis yTemps;
    @FXML
    CategoryAxis xTemps;
    @FXML
    Label lblChoosenFile;

    @FXML
    RadioButton radioCmpGA;
    @FXML
    RadioButton radioCmpPSO;
    @FXML
    RadioButton radioCmpBSO;

    @FXML
    Button btnComparaison;


    @FXML Label lblTemps;
    @FXML Label lblSat;

    String[] barColors = {"#e91e63", "#ffb300", "#b0003a", "#ff6090", "#ffe54c", "#c68400",
            "#8c0032", "#7e57c2", "#b085f5", "#66ff33"};


    boolean ga = true, pso = false, bso = false;
    String file = null;

    ArrayList<Integer> fit = new ArrayList<>();
    ArrayList<Double> delta = new ArrayList<>();


    double temps_moy = 0;
    double sat_moy = 0;

    public void initialize(URL location, ResourceBundle resources) {

        btnFixer.setOnAction(this::fixerParam);
        btnInitialiser.setOnAction(this::initialiserParam);
        btnRun.setOnAction(this::run);
        btnChooseFile.setOnAction(this::chooseFile);
        btnComparaison.setOnAction(this::comparaison);
    }


    private void comparaison(ActionEvent event) {

        int ga_cmp = 0;
        int bso_cmp = 0;
        int pso_cmp = 0;

        barchartTemps.getData().clear();
        barchartSat.getData().clear();

        if (radioCmpGA.isSelected())
            ga_cmp = 1;
        if (radioCmpBSO.isSelected())
            bso_cmp = 1;
        if (radioCmpPSO.isSelected())
            pso_cmp = 1;

        lblTemps.setText("Histogramme de temps d'exécution pour chaque algorithme");
        lblSat.setText("Histogramme de taux de satisfiabilité pour chaque algorithme");

        barchartSat.getData().clear();
        barchartTemps.getData().clear();

        xSat.setLabel("Algorithme utilisé");
        xTemps.setLabel("Algorithme utilisé");


        SAT sat = new SAT(fichier, Main.VARIABLENBR, Main.CLAUSENBR);

        ArrayList<Integer> ga_fit = new ArrayList<>();
        ArrayList<Integer> pso_fit = new ArrayList<>();
        ArrayList<Integer> bso_fit = new ArrayList<>();

        ArrayList<Double> ga_delta = new ArrayList<>();
        ArrayList<Double> pso_delta = new ArrayList<>();
        ArrayList<Double> bso_delta = new ArrayList<>();

        if (ga_cmp == 1) {
            Population algo_ga = new Population(population, Main.VARIABLENBR, mutation, 35, sat);
            for (int i = 1; i <= nbrEssai; i++) {
                t1 = System.currentTimeMillis();
                ga_fit.add(algo_ga.traitementWithImmigration(nbrIterationGA, Main.TARGET, 500));
                t2 = System.currentTimeMillis();
                ga_delta.add((t2 - t1) / 1000);
            }
        }
        if (bso_cmp == 1) {
            BSO algo_bso = new BSO(sat);
            for (int i = 1; i <= nbrEssai; i++) {
                t1 = System.currentTimeMillis();
                bso_fit.add(algo_bso.run(flip, Main.TARGET, nbrBee, chance, nbrIterationBSO
                        , nbrLocalBee));
                t2 = System.currentTimeMillis();
                bso_delta.add((t2 - t1) / 1000);
            }
        }
        if (pso_cmp == 1) {
            PSO algo_pso = new PSO((int) particule, sat, c1, c2, w);
            for (int i = 1; i <= nbrEssai; i++) {
                t1 = System.currentTimeMillis();
                pso_fit.add(algo_pso.traitement((int) nbrIterationPSO, Main.TARGET));
                t2 = System.currentTimeMillis();
                pso_delta.add((t2 - t1) / 1000);
            }
        }

        paneResult.setVisible(true);

        double temsp_moy_ga = 0;
        double temsp_moy_pso = 0;
        double temsp_moy_bso = 0;

        double sat_moy_ga = 0;
        double sat_moy_pso = 0;
        double sat_moy_bso = 0;

        String t = "\nRésultats de comparaison :\n    Fichier : "+file+"\n    Nombre d'essais : "+nbrEssai+"\n-------------------------------------";
        for (int i = 1; i <= nbrEssai; i++) {
            if (ga_cmp == 1) {
                temsp_moy_ga = temsp_moy_ga + ga_delta.get(i - 1);
                sat_moy_ga = sat_moy_ga + ga_fit.get(i - 1);
            }
            if (bso_cmp == 1) {
                temsp_moy_bso = temsp_moy_bso + bso_delta.get(i - 1);
                sat_moy_bso = sat_moy_bso + bso_fit.get(i - 1);
            }
            if (pso_cmp == 1) {
                temsp_moy_pso = temsp_moy_pso + pso_delta.get(i - 1);
                sat_moy_pso = sat_moy_pso + pso_fit.get(i - 1);
            }
        }

        temsp_moy_pso = temsp_moy_pso / nbrEssai;
        temsp_moy_ga = temsp_moy_ga / nbrEssai;
        temsp_moy_bso = temsp_moy_bso / nbrEssai;

        sat_moy_bso = sat_moy_bso / nbrEssai;
        sat_moy_ga = sat_moy_ga / nbrEssai;
        sat_moy_pso = sat_moy_pso / nbrEssai;

        XYChart.Series<String, Double> chartSat = new XYChart.Series<String, Double>();
        XYChart.Series<String, Double> chartTemps = new XYChart.Series<String, Double>();

        chartSat.getData().clear();
        chartTemps.getData().clear();

        if(bso_cmp==1) {

            t = t + "\nAlgorithme BSO\n";
            t=t+"   Le temps d'exécution moyen : "+temsp_moy_bso+" s.\n";
            t=t+"   Le taux de satisfiabilité moyen  : "+sat_moy_bso+".\n";
            t=t+"   Le pourcentage de satisfiabilité moyen  : "+(sat_moy_bso*100/325)+".\n-------------------------------------";

            XYChart.Data<String, Double> dsat = new XYChart.Data<>("BSO", sat_moy_bso);
            dsat.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                    if (newValue != null) {
                        newValue.setStyle("-fx-bar-fill: " + barColors[5] + ";");
                    }
                }
            });
            chartSat.getData().add(dsat);

            XYChart.Data<String, Double> dtemps = new XYChart.Data<>("BSO", temsp_moy_bso);
            dtemps.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                    if (newValue != null) {
                        newValue.setStyle("-fx-bar-fill: " + barColors[5] + ";");
                    }
                }
            });
            chartTemps.getData().add(dtemps);
        }

        if(ga_cmp==1) {

            t = t + "\nAlgorithme GA\n";
            t=t+"   Le temps d'exécution moyen : "+temsp_moy_ga+" s.\n";
            t=t+"   Le taux de satisfiabilité moyen  : "+sat_moy_ga+".\n";
            t=t+"   Le pourcentage de satisfiabilité moyen  : "+(sat_moy_ga*100/325)+"%.\n-------------------------------------";



            XYChart.Data<String, Double> dsat = new XYChart.Data<>("GA", sat_moy_ga);
            dsat.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                    if (newValue != null) {
                        newValue.setStyle("-fx-bar-fill: " + barColors[3] + ";");
                    }
                }
            });
            chartSat.getData().add(dsat);

            XYChart.Data<String, Double> dtemps = new XYChart.Data<>("GA", temsp_moy_ga);
            dtemps.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                    if (newValue != null) {
                        newValue.setStyle("-fx-bar-fill: " + barColors[3] + ";");
                    }
                }
            });
            chartTemps.getData().add(dtemps);
        }

        if(pso_cmp==1) {


            t = t + "\nAlgorithme PSO\n";
            t=t+"   Le temps d'exécution moyen : "+temsp_moy_pso+" s.\n";
            t=t+"   Le taux de satisfiabilité moyen  : "+sat_moy_pso+".\n";
            t=t+"   Le pourcentage de satisfiabilité moyen  : "+(sat_moy_pso*100/325)+"%.\n-------------------------------------";

            XYChart.Data<String, Double> dsat = new XYChart.Data<>("PSO", sat_moy_pso);
            dsat.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                    if (newValue != null) {
                        newValue.setStyle("-fx-bar-fill: " + barColors[8] + ";");
                    }
                }
            });
            chartSat.getData().add(dsat);

            XYChart.Data<String, Double> dtemps = new XYChart.Data<>("PSO", temsp_moy_pso);
            dtemps.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                    if (newValue != null) {
                        newValue.setStyle("-fx-bar-fill: " + barColors[8] + ";");
                    }
                }
            });
            chartTemps.getData().add(dtemps);
        }



        Text t1 = new Text(t);
        t1.setFont(Font.font("System Bold", 18));
        t1.setFill(Color.valueOf("#8c0032"));

        txtResult.getChildren().clear();
        txtResult.getChildren().add(t1);

        barchartSat.getData().add(chartSat);
        barchartTemps.getData().add(chartTemps);

        radioCmpGA.setSelected(false);
        radioCmpBSO.setSelected(false);
        radioCmpPSO.setSelected(false);
    }

    private void chooseFile(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(null);
            file = selectedFile.getPath();
            //System.out.println(file);
            lblChoosenFile.setText("Le fichier choisi : " + file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void run(ActionEvent actionEvent) {

        //clearing the barchar
        barchartTemps.getData().clear();
        barchartSat.getData().clear();

        //add if there r no file imported !
        if (file == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur ..... ");
            alert.setHeaderText("Vous n'avez pas choisi un fichier !");
            alert.setContentText("Veuillez choisir un fichier ou importer un !");
            alert.showAndWait();
        } else {

            //verifier si le nombre d'essais est modifié

            if (comboNbrEssai.getSelectionModel().getSelectedItem() != null) {
                nbrEssai = Integer.parseInt(comboNbrEssai.getSelectionModel().getSelectedItem().toString());
            }

            if (!file.startsWith("u"))
                fichier = Main.readFile(file, true);
            else
                fichier = Main.readFile(file);

            SAT sat = new SAT(fichier, Main.VARIABLENBR, Main.CLAUSENBR);

            if (ga) {
                radioCmpGA.setSelected(true);
                txtResult.getChildren().add((new Text("Veuillez patienter ...")));
                Population algo_ga = new Population(population, Main.VARIABLENBR, mutation, 35, sat);
                for (int i = 1; i <= nbrEssai; i++) {
                    t1 = System.currentTimeMillis();
                    fit.add(algo_ga.traitementWithImmigration(nbrIterationGA, Main.TARGET, 500));
                    t2 = System.currentTimeMillis();
                    delta.add((t2 - t1) / 1000);
                }
            }
            if (bso) {
                radioCmpBSO.setSelected(true);
                BSO algo_bso = new BSO(sat);
                for (int i = 1; i <= nbrEssai; i++) {
                    t1 = System.currentTimeMillis();
                    fit.add(algo_bso.run(flip, Main.TARGET, nbrBee, chance, nbrIterationBSO
                            , nbrLocalBee));
                    t2 = System.currentTimeMillis();
                    delta.add((t2 - t1) / 1000);
                }
            }
            if (pso) {
                radioCmpPSO.setSelected(true);
                PSO algo_pso = new PSO((int) particule, sat, c1, c2, w);
                for (int i = 1; i <= nbrEssai; i++) {
                    t1 = System.currentTimeMillis();
                    fit.add(algo_pso.traitement((int) nbrIterationPSO, Main.TARGET));
                    t2 = System.currentTimeMillis();
                    delta.add((t2 - t1) / 1000);
                }
            }

            paneResult.setVisible(true);

            String txt = "    Fichier : " + file + "\n-----------------------------------------------";

            temps_moy = 0;
            sat_moy = 0;

            for (int i = 1; i <= nbrEssai; i++) {
                txt = txt + "\n    Nombre d'essai : " + i + "\n    Nombre de clause satisfaits : " + fit.get(i - 1) + "\n    Temps d'exécution : " + delta.get(i - 1) + " s";
                txt = txt + "\n-----------------------------------------------";
                temps_moy = temps_moy + delta.get(i - 1);
                sat_moy = sat_moy + fit.get(i - 1);
            }

            txt = txt + "\n    Temps d'exécution moyen : " + (temps_moy / nbrEssai) + " s";
            txt = txt + "\n    Taux de satisfiabilité moyen : " + (sat_moy / nbrEssai) + "\n\n";

            Text t1 = new Text(txt);
            t1.setFont(Font.font("System Bold", 18));
            t1.setFill(Color.valueOf("#8c0032"));

            txtResult.getChildren().clear();
            txtResult.getChildren().add(t1);

            paneGraph.setVisible(true);
            paneGraph.setDisable(false);

            xSat.setLabel("Nombre d'essai");
            xTemps.setLabel("Nombre d'essai");

            yTemps.setLabel("Temps d'exécution (sec)");
            yTauxSat.setLabel("Taux de satisfiabilité /325");

            XYChart.Series<String, Integer> chartSat = new XYChart.Series<String, Integer>();
            XYChart.Series<String, Double> chartTemps = new XYChart.Series<String, Double>();

            chartSat.getData().clear();
            chartTemps.getData().clear();

            barchartSat.getData().clear();
            barchartTemps.getData().clear();

            for (int i = 1; i <= nbrEssai; i++) {
                XYChart.Data<String, Integer> dsat = new XYChart.Data<>("" + i, fit.get(i - 1));
                dsat.nodeProperty().addListener(new ChangeListener<Node>() {
                    @Override
                    public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                        if (newValue != null) {
                            newValue.setStyle("-fx-bar-fill: " + barColors[Integer.parseInt(dsat.getXValue()) - 1] + ";");
                        }
                    }
                });
                chartSat.getData().add(dsat);

                XYChart.Data<String, Double> dtemps = new XYChart.Data<>("" + i, delta.get(i - 1));
                dtemps.nodeProperty().addListener(new ChangeListener<Node>() {
                    @Override
                    public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                        if (newValue != null) {
                            newValue.setStyle("-fx-bar-fill: " + barColors[Integer.parseInt(dsat.getXValue()) - 1] + ";");
                        }
                    }
                });

                chartTemps.getData().add(dtemps);
            }

            barchartSat.getData().add(chartSat);
            barchartTemps.getData().add(chartTemps);

        }


    }


    @FXML
    private void chooseFile1(ActionEvent event) {
        file = "uf75-01.cnf";
        lblChoosenFile.setText("Le fichier choisi : " + file);
    }

    @FXML
    private void chooseFile2(ActionEvent event) {
        file = "uf75-02.cnf";
        lblChoosenFile.setText("Le fichier choisi : " + file);

    }

    @FXML
    private void chooseFile3(ActionEvent event) {
        file = "uf75-03.cnf";
        lblChoosenFile.setText("Le fichier choisi : " + file);

    }

    @FXML
    private void chooseFile4(ActionEvent event) {
        file = "uf75-04.cnf";
        lblChoosenFile.setText("Le fichier choisi : " + file);

    }

    @FXML
    private void chooseFile5(ActionEvent event) {
        file = "uf75-05.cnf";
        lblChoosenFile.setText("Le fichier choisi : " + file);

    }

    @FXML
    private void chooseFile6(ActionEvent event) {
        file = "uf75-06.cnf";
        lblChoosenFile.setText("Le fichier choisi : " + file);

    }

    @FXML
    private void chooseFile7(ActionEvent event) {
        file = "uf75-07.cnf";
        lblChoosenFile.setText("Le fichier choisi : " + file);

    }

    @FXML
    private void chooseFile8(ActionEvent event) {
        file = "uf75-08.cnf";
        lblChoosenFile.setText("Le fichier choisi : " + file);

    }

    @FXML
    private void chooseFile9(ActionEvent event) {
        file = "uf75-09.cnf";
        lblChoosenFile.setText("Le fichier choisi : " + file);

    }

    @FXML
    private void chooseFile10(ActionEvent event) {
        file = "uf75-010.cnf";
        lblChoosenFile.setText("Le fichier choisi : " + file);

    }


    private void fixerParam(ActionEvent actionEvent) {

        if (ga) {
            if (combboPopulation.getSelectionModel().getSelectedItem() != null)
                population = Integer.parseInt(combboPopulation.getSelectionModel().getSelectedItem().toString());

            if (comboMutation.getSelectionModel().getSelectedItem() != null)
                mutation = Integer.parseInt(comboMutation.getSelectionModel().getSelectedItem().toString());

            if (comboNbrItGA.getSelectionModel().getSelectedItem() != null)
                nbrIterationGA = Integer.parseInt(comboNbrItGA.getSelectionModel().getSelectedItem().toString());
        }
        if (pso) {

            if (comboNbrItPSO.getSelectionModel().getSelectedItem() != null)
                nbrIterationPSO = Integer.parseInt(comboNbrItPSO.getSelectionModel().getSelectedItem().toString());

            if (comboParticule.getSelectionModel().getSelectedItem() != null)
                particule = Integer.parseInt(comboParticule.getSelectionModel().getSelectedItem().toString());

            if (comboC1.getSelectionModel().getSelectedItem() != null)
                c1 = Double.parseDouble(comboC1.getSelectionModel().getSelectedItem().toString());

            if (comboC2.getSelectionModel().getSelectedItem() != null)
                c2 = Double.parseDouble(comboC2.getSelectionModel().getSelectedItem().toString());

            if (comboW.getSelectionModel().getSelectedItem() != null)
                w = Integer.parseInt(comboW.getSelectionModel().getSelectedItem().toString());
        }
        if (bso) {

            if (comboNbrItBSO.getSelectionModel().getSelectedItem() != null)
                nbrIterationBSO = Integer.parseInt(comboNbrItBSO.getSelectionModel().getSelectedItem().toString());

            if (comboFlip.getSelectionModel().getSelectedItem() != null)
                flip = Integer.parseInt(comboFlip.getSelectionModel().getSelectedItem().toString());

            if (comboNbChance.getSelectionModel().getSelectedItem() != null)
                chance = Integer.parseInt(comboNbChance.getSelectionModel().getSelectedItem().toString());

            if (comboNbrBee.getSelectionModel().getSelectedItem() != null)
                nbrBee = Integer.parseInt(comboNbrBee.getSelectionModel().getSelectedItem().toString());

            if (comboNbrLocalBee.getSelectionModel().getSelectedItem() != null)
                nbrLocalBee = Integer.parseInt(comboNbrLocalBee.getSelectionModel().getSelectedItem().toString());
        }

        if (comboNbrEssai.getSelectionModel().getSelectedItem() != null) {
            nbrEssai = Integer.parseInt(comboNbrEssai.getSelectionModel().getSelectedItem().toString());
        }
    }

    private void initialiserParam(ActionEvent event) {

        if (ga) {

            nbrIterationGA = 1020;
            mutation = 5;
            population = 24;
        }
        if (bso) {
            nbrIterationBSO = 2540;
            flip = 1;
            nbrBee = 18;
            nbrLocalBee = 26;
            chance = 5;

        }
        if (pso) {
            nbrIterationPSO = 1990;
            particule = 20;
            c1 = 1.1;
            c2 = 1.3;
            w = 0.625;
        }
        nbrEssai = 3;

        file = null;
    }

    @FXML
    private void algoGA(ActionEvent actionEvent) {

        comboAlgo.setText("Algorithme génétique GA");
        ga = true;
        pso = false;
        bso = false;

        paneReglageGA.setVisible(true);
        paneReglageGA.setDisable(false);

        paneReglageBSO.setVisible(false);
        paneReglageBSO.setDisable(true);

        paneReglagePSO.setVisible(false);
        paneReglagePSO.setDisable(true);

        paneParam.setVisible(true);
        paneParam.setDisable(false);
    }

    @FXML
    private void algoPSO(ActionEvent actionEvent) {
        comboAlgo.setText("Algorithme d'essaim de particule PSO");

        ga = false;
        pso = true;
        bso = false;

        paneReglageGA.setVisible(false);
        paneReglageGA.setDisable(true);

        paneReglageBSO.setVisible(false);
        paneReglageBSO.setDisable(true);

        paneReglagePSO.setVisible(true);
        paneReglagePSO.setDisable(false);

        paneParam.setVisible(true);
        paneParam.setDisable(false);
    }

    @FXML
    private void algoBSO(ActionEvent actionEvent) {
        comboAlgo.setText("Algorithme d'essaim d'abeilles BSO");
        ga = false;
        pso = false;
        bso = true;

        paneReglageGA.setVisible(false);
        paneReglageGA.setDisable(true);

        paneReglageBSO.setVisible(true);
        paneReglageBSO.setDisable(false);

        paneReglagePSO.setVisible(false);
        paneReglagePSO.setDisable(true);

        paneParam.setVisible(true);
        paneParam.setDisable(false);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("app.fxml"));
        primaryStage.setTitle("");
        // primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}
