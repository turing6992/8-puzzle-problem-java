/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.io.File;
import java.util.Random;
import java.util.Vector;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.stage.FileChooser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

public class Main extends Application {
    public int missing_piece; //missing_piece represents the position where has no image there or blank area
    int[] number;// = {0, 1, 2, 3, 5, 4, 6, 7, 8};
    int complexity = 30;

    RandomGenerator randgen; //the random puzzle patten generator


    /* This function will swap two numbers at the positions first_num and second_num: a[first_num], b[second_num]*/
    public void swapNums(int first_num, int second_num) // swap two images
    {
        int row1 = GridPane.getRowIndex(imageViews[first_num]);
        int colu1 = GridPane.getColumnIndex(imageViews[first_num]);
        int row2 = GridPane.getRowIndex(imageViews[second_num]);
        int colu2 = GridPane.getColumnIndex(imageViews[second_num]);

        GridPane.setRowIndex(imageViews[first_num], row2);
        GridPane.setColumnIndex(imageViews[first_num], colu2);
        GridPane.setRowIndex(imageViews[second_num], row1);
        GridPane.setColumnIndex(imageViews[second_num], colu1);

        // swap the value of image;
        int temp;
        int swap_idx1, swap_idx2;
        swap_idx1 = 3 * row1 + colu1;
        swap_idx2 = 3 * row2 + colu2;
        temp = number[swap_idx1];
        number[swap_idx1] = number[swap_idx2];
        number[swap_idx2] = temp;
        System.out.println("missing number: " + missing_piece);
        System.out.print(number[0] + " ");
        System.out.print(number[1] + " ");
        System.out.println(number[2] + " ");
        System.out.print(number[3] + " ");
        System.out.print(number[4] + " ");
        System.out.println(number[5] + " ");
        System.out.print(number[6] + " ");
        System.out.print(number[7] + " ");
        System.out.println(number[8] + " ");
        System.out.println("******");
    }

    public ImageView emptimg = new ImageView();
    ImageView[] imageViews = new ImageView[9];

    private String fileName;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage arg0) throws Exception {
        init(arg0);
    }

    public void init(Stage stage) {
        randgen = new RandomGenerator();
        number = new int[9];
        missing_piece = randgen.getRandomSeq(number, complexity); //generate missing number


        fileName = "image/image.jpg";
        Image image = new Image(fileName);
        GridPane gridPane = new GridPane();
        for (int i = 0, k = 0; i <= 2; ++i) {
            for (int j = 0; j <= 2; ++j, ++k) {
                imageViews[k] = new ImageView(image);// initial the array
                imageViews[k].setOnMouseClicked(new myevent());// set mouse
                // click
                // function
                imageViews[k].setViewport(new Rectangle2D(100 * j, 100 * i, 100, 100));// cut
                // the
                // image
                // to
                // 9
                // pieces
            }
        }

        gridPane.add(imageViews[number[0]], 0, 0);// add imageviews to grid pane
        // as same as random
        // sequence
        gridPane.add(imageViews[number[1]], 1, 0);
        gridPane.add(imageViews[number[2]], 2, 0);
        gridPane.add(imageViews[number[3]], 0, 1);
        gridPane.add(imageViews[number[4]], 1, 1);
        gridPane.add(imageViews[number[5]], 2, 1);
        gridPane.add(imageViews[number[6]], 0, 2);
        gridPane.add(imageViews[number[7]], 1, 2);
        gridPane.add(imageViews[number[8]], 2, 2);

        ImageView comp = new ImageView(image);
        comp.setViewport(new Rectangle2D(0, 0, 300, 300));
        Image image2 = new Image("image/white.png");
        imageViews[missing_piece].setImage(image2);// use to set white image
        gridPane.setGridLinesVisible(true);
        BorderPane borderPane = new BorderPane(gridPane);
        VBox right = new VBox(20, comp);
        HBox buttons = new HBox(50);
        buttons.setPadding(new Insets(20));
        Button open = new Button("open");// a button to set another image
        final FileChooser fileChooser = new FileChooser();
        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    fileName = file.getAbsolutePath();
                    resetStage(stage);
                    System.out.print(fileName);
                }
            }
        });
        Button reset = new Button("reset");
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                init(stage);
            }
        });
        Button solution = new Button("solution");
        solution.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event1) {
                Vector solution_path;
                solution_path = new Vector();

                Solution.breadthFirstSearch(number, missing_piece, solution_path);

                System.out.println("SLN path");
                System.out.println(solution_path);
                if (solution_path.size() > 1) {
                    final IntegerProperty i = new SimpleIntegerProperty(0);
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                        i.set(i.get() + 1);
                        swapNums(number[(int) solution_path.get(i.get() - 1)], number[(int) solution_path.get(i.get())]);
                    }));
                    timeline.setCycleCount(solution_path.size() - 1);
                    timeline.play();
                }
            }
        });
        buttons.getChildren().addAll(open, reset, solution);
        borderPane.setRight(right);
        borderPane.setBottom(buttons);
        buttons.setAlignment(Pos.CENTER);
        Scene scene = new Scene(borderPane, 650, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    public void resetStage(Stage stage) // use to restart current image
    {

        missing_piece = randgen.getRandomSeq(number, complexity); //generate missing number
        Image image = new Image("file:///" + fileName);// for mac os
        // Image image = new Image("file://"+fileName);//for windows os
        GridPane gridPane = new GridPane();
        for (int i = 0, k = 0; i <= 2; ++i) {
            for (int j = 0; j <= 2; ++j, ++k) {
                imageViews[k] = new ImageView(image);
                imageViews[k].setOnMouseClicked(new myevent());
                imageViews[k].setViewport(new Rectangle2D(100 * j, 100 * i, 100, 100));
            }
        }

        gridPane.add(imageViews[number[0]], 0, 0);
        gridPane.add(imageViews[number[1]], 1, 0);
        gridPane.add(imageViews[number[2]], 2, 0);
        gridPane.add(imageViews[number[3]], 0, 1);
        gridPane.add(imageViews[number[4]], 1, 1);
        gridPane.add(imageViews[number[5]], 2, 1);
        gridPane.add(imageViews[number[6]], 0, 2);
        gridPane.add(imageViews[number[7]], 1, 2);
        gridPane.add(imageViews[number[8]], 2, 2);

        ImageView comp = new ImageView(image);
        comp.setViewport(new Rectangle2D(0, 0, 300, 300));
        Image image2 = new Image("image/white.png");
        imageViews[missing_piece].setImage(image2);
        gridPane.setGridLinesVisible(true);
        BorderPane borderPane = new BorderPane(gridPane);
        VBox right = new VBox(20, comp);
        HBox buttons = new HBox(50);
        buttons.setPadding(new Insets(20));
        Button open = new Button("open");
        final FileChooser fileChooser = new FileChooser();
        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    fileName = file.getAbsolutePath();
                    resetStage(stage);
                    System.out.print(fileName);
                }
            }
        });

        Button reset = new Button("reset");
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resetStage(stage);
            }
        });
        Button solution = new Button("solution");
        solution.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event1) {
                Vector solution_path;
                solution_path = new Vector();

                Solution.breadthFirstSearch(number, missing_piece, solution_path);


                if (solution_path.size() > 1) {
                    final IntegerProperty i = new SimpleIntegerProperty(0);
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                        i.set(i.get() + 1);
                        swapNums(number[(int) solution_path.get(i.get() - 1)], number[(int) solution_path.get(i.get())]);
                    }));
                    timeline.setCycleCount(solution_path.size() - 1);
                    timeline.play();
                }
            }
        });
        buttons.getChildren().addAll(open, reset, solution);
        borderPane.setRight(right);
        borderPane.setBottom(buttons);
        buttons.setAlignment(Pos.CENTER);
        Scene scene = new Scene(borderPane, 650, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }



    class myevent implements EventHandler<MouseEvent> // implement click
            // function
    {

        @Override
        public void handle(MouseEvent arg0) {
            // TODO Auto-generated method stub
            ImageView img = (ImageView) arg0.getSource();
            int row = GridPane.getRowIndex(img);
            int colu = GridPane.getColumnIndex(img);
            int idx = row * 3 + colu;
            int mrow = GridPane.getRowIndex(imageViews[missing_piece]);
            int mcolu = GridPane.getColumnIndex(imageViews[missing_piece]);
            int midx = mrow * 3 + mcolu;
            double sx = img.getLayoutX();
            double sy = img.getLayoutY();
            double dispx = sx - imageViews[missing_piece].getLayoutX();
            double dispy = sy - imageViews[missing_piece].getLayoutY();
            if ((dispx == -100) && (dispy == 0)) // click the left image of
            // white image
            {
                swapNums(number[idx], number[midx]);// swap two images
                if (issucc(imageViews)) // check successful
                {
                    Alert alert = new Alert(AlertType.WARNING, "Success！");
                    alert.show();
                }
            }

            else if ((dispx == 0) && (dispy == -100)) // click the top image of
            // white image
            {
                swapNums(number[idx], number[midx]);
                if (issucc(imageViews)) {
                    Alert alert = new Alert(AlertType.WARNING, "Success！");
                    alert.show();
                }
            } else if ((dispx == 100) && (dispy == 0)) // click the right image
            // of white image
            {
                swapNums(number[idx], number[midx]);
                if (issucc(imageViews)) {
                    Alert alert = new Alert(AlertType.WARNING, "Success！");
                    alert.show();
                }
            } else if ((dispx == 0) && (dispy == 100)) // click the bottom image
            // of white image
            {
                swapNums(number[idx], number[midx]);
                if (issucc(imageViews)) {
                    Alert alert = new Alert(AlertType.WARNING, "Success！");
                    alert.show();
                }
            }
        }

    }

    public boolean issucc(ImageView[] imageViews) // check game successful
    {
        for (int i = 0; i <= 9; ++i) {
            if (i != 3 * GridPane.getRowIndex(imageViews[i]) + GridPane.getColumnIndex(imageViews[i])) {
                return false;
            }
        }
        return true;
    }

}

class RandomGenerator {
    private Vector moving_directions[];

    RandomGenerator() {
        moving_directions = new Vector[9];

        for(int i = 0; i < 9; i++)
        {
            moving_directions[i] = new Vector();
        }

        moving_directions[0].add(3); // for each position, tell the piece-8
        // which one in the array that it can be
        // swapped with.
        // So only two pieces are considered each time, when the piece-8 is
        // moving.
        moving_directions[0].add(1);

        moving_directions[1].add(0);
        moving_directions[1].add(4);
        moving_directions[1].add(2);

        moving_directions[2].add(1);
        moving_directions[2].add(5);

        moving_directions[3].add(6);
        moving_directions[3].add(4);
        moving_directions[3].add(0);

        moving_directions[4].add(3);
        moving_directions[4].add(7);
        moving_directions[4].add(5);
        moving_directions[4].add(1);

        moving_directions[5].add(4);
        moving_directions[5].add(8);
        moving_directions[5].add(2);

        moving_directions[6].add(7);
        moving_directions[6].add(3);

        moving_directions[7].add(6);
        moving_directions[7].add(8);
        moving_directions[7].add(4);

        moving_directions[8].add(7);
        moving_directions[8].add(5);
    }

    int getRandomSeq(int[] num, int steps)
    {

        Random random = new Random();
        int missing = random.nextInt(8);
        //num = new int[9];
        for(int i = 0; i < 9; i++)
        {
            num[i] = i;
        }

        int cur_pos = missing; //The position of the 8-piece
        int swap_pos; //The position that is used to be swapped with the 8-piece
        int temp; //Used for data swap
        int choices; //The number of choiece for different positions of 8-piece
        int x; //The choice randomly made from different choieces
        for(int i = 0; i < steps; i++)
        {
            int options = moving_directions[cur_pos].size();
            x = random.nextInt(options - 1);
            swap_pos = (int)moving_directions[cur_pos].get(x);
            temp = num[cur_pos];
            num[cur_pos] = num[swap_pos];
            num[swap_pos] = temp;
            cur_pos = swap_pos;
        }

        return missing;
    }

}