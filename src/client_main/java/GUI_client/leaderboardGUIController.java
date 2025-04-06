package GUI_client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Callback;
import leaderboard.Leaderboard;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class leaderboardGUIController implements Initializable{

    private static final String GAME_CHECKERS = "CHECKERS";
    private static final String GAME_TTT = "TTT";
    private static final String GAME_C4 = "C4";

    private String selectedSort = "RATING"; // default sort if needed
    private String selectedGame;
    private Leaderboard leaderboard;
    private boolean friendSearch = false;

    @FXML
    public ImageView muteButton;
    @FXML
    public ImageView backButton;
    @FXML
    public ImageView checkers_button; // action: checkers_stats
    @FXML
    public ImageView TTT_button;      // action: TTT_stats
    @FXML
    public ImageView C4_button;       // action: C4_stats
    @FXML
    public ImageView search_button;   // action: send_search
    @FXML
    public SplitMenuButton filter_stats; // action: filter
    @FXML
    public SplitMenuButton sort_stats; // sort by specifications
    @FXML
    public SplitMenuButton order_stats; // sort ordering
    @FXML
    public TextField friend_search;   // action: search
    @FXML
    public Button toggleOrder; // action: toggleOrder()
    @FXML
    public ImageView back;
    @FXML
    public MenuItem sortByWins;
    @FXML
    public MenuItem sortByWLR;
    @FXML
    public MenuItem sortByRating;

    @FXML
    public TableColumn<ObservableList<String>, String> position;
    @FXML
    public TableColumn<ObservableList<String>, String> username;
    @FXML
    public TableColumn<ObservableList<String>, String> rating;
    @FXML
    public TableColumn<ObservableList<String>, String> wins;
    @FXML
    public TableColumn<ObservableList<String>, String> wlr;

    @FXML
    public TableView<ObservableList<String>> C4_table;

    /**
     * Sets up variable row for profiles
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Make the TableView background transparent
        C4_table.setBackground(Background.EMPTY);
        C4_table.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-control-inner-background: transparent;" +
                        "-fx-control-inner-background-alt: transparent;" +
                        "-fx-table-cell-border-color: transparent;" +
                        "-fx-table-header-border-color: transparent;"
        );

        // Disable row highlighting
        C4_table.setSelectionModel(null);

        final String cellStyle = "-fx-background-color: transparent; -fx-text-fill: #00d8ff; -fx-font-weight: bold;";

        // Set up each column using the helper method
        setupTableColumn(position, 0);
        setupTableColumn(username, 2);
        setupTableColumn(wlr, 3);
        setupTableColumn(rating, 4);
        setupTableColumn(wins, 5);

        // Prevent horizontal scrolling by forcing columns to fill the available width
        C4_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // setup soundtrack and mute status
        String path = Objects.requireNonNull(getClass().getResource("/music/leaderboardsTrack.mp3")).toExternalForm(); // or absolute path
        Media sound = new Media(path);
        AudioManager.mediaPlayer = new MediaPlayer(sound);
        AudioManager.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        AudioManager.mediaPlayer.play();
        if (AudioManager.isMuted()){
            AudioManager.applyMute();
            muteButton.setImage(new Image("muteButton.png"));
        } else{
            muteButton.setImage(new Image("unmuteButton.png"));
        }
    }

    // method to set up a TableColumn
    private void setupTableColumn(TableColumn<ObservableList<String>, String> column, final int index) {
        // extract the value at the specified index
        column.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList<String>, String> param) {
                return new SimpleStringProperty(param.getValue().get(index));
            }
        });

        column.setCellFactory(new Callback<TableColumn<ObservableList<String>, String>, TableCell<ObservableList<String>, String>>() {
            @Override
            public TableCell<ObservableList<String>, String> call(TableColumn<ObservableList<String>, String> param) {
                return new TableCell<ObservableList<String>, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {

                        super.updateItem(item, empty);
                        setAlignment(javafx.geometry.Pos.CENTER);

                        if (empty || item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            /*
                            https://www.reddit.com/r/answers/comments/jzy9l8/what_color_contrasts_both_black_and_white_the_most/

                            Source for colours that contrast with black and white.
                             */
                            setText(item);
                            int rowIndex = getIndex();
                            String color;
                            if (rowIndex % 4 == 0) {
                                color = "#6cff04";
                            } else if (rowIndex % 4 == 1) {
                                color = "#ffa200";
                            } else if (rowIndex % 4 == 2) {
                                color = "#faed27";
                            } else { // rowIndex % 4 == 3
                                color = "#00d8ff";
                            }
                            setStyle("-fx-background-color: transparent; -fx-text-fill: " + color + "; -fx-font-weight: bold;");

                        }
                    }
                };
            }
        });
    }

    /**
     * displays leaderboard based on selected game and selected sorting specifications
     *
     * @param selectedSort
     * @param selectedGame
     */
    public void displayLeaderboard(String selectedSort, String selectedGame) {
        leaderboard = new Leaderboard(selectedSort, selectedGame);
        ArrayList<ArrayList<String>> rankingData = leaderboard.getRankings();

        // Recalculate leaderboard positions (1-indexed)
        for (int i = 0; i < rankingData.size(); i++) {
            rankingData.get(i).set(0, String.valueOf(i + 1));
        }

        // Convert the data into an ObservableList for the TableView
        ObservableList<ObservableList<String>> tableData = FXCollections.observableArrayList();
        for (ArrayList<String> row : rankingData) {
            ObservableList<String> observableRow = FXCollections.observableArrayList(row);
            tableData.add(observableRow);
        }

        C4_table.setItems(tableData);
    }

    public void C4_stats(MouseEvent mouseEvent) {
        this.selectedGame = GAME_C4;
        displayLeaderboard(this.selectedSort, this.selectedGame);
    }

    public void checkers_stats(MouseEvent mouseEvent) {
        this.selectedGame = GAME_CHECKERS;
        displayLeaderboard(this.selectedSort, this.selectedGame);
    }

    public void TTT_stats(MouseEvent mouseEvent) {
        this.selectedGame = GAME_TTT;
        displayLeaderboard(this.selectedSort, this.selectedGame);
    }

    /**
     * Triggers when "Toggle Sort Order" button is pressed
     *
     * @param actionEvent
     */
    public void toggleOrder(ActionEvent actionEvent) {
        // Ensure the leaderboard instance exists
        if (leaderboard != null) {
            // Get rankings
            ArrayList<ArrayList<String>> rankingData = leaderboard.getRankings();

            // Toggle the order on the existing leaderboard instance
            leaderboard.toggleSortOrder(rankingData);
            rankingData = leaderboard.getRankings();

            // Recalculate leaderboard positions (1-indexed)
            for (int i = 0; i < rankingData.size(); i++) {
                rankingData.get(i).set(0, String.valueOf(i + 1));
            }

            // Convert the data into an ObservableList for the TableView
            ObservableList<ObservableList<String>> tableData = FXCollections.observableArrayList();
            for (ArrayList<String> row : rankingData) {
                ObservableList<String> observableRow = FXCollections.observableArrayList(row);
                tableData.add(observableRow);
            }

            // Update the TableView with the NEW ordering
            C4_table.setItems(tableData);
        }
    }

    public void sortByWins(ActionEvent actionEvent) {
        this.selectedSort = "WINS";
        displayLeaderboard(this.selectedSort, this.selectedGame);
    }
    public void sortByWLR(ActionEvent actionEvent) {
        this.selectedSort = "WLR";
        displayLeaderboard(this.selectedSort, this.selectedGame);
    }
    public void sortByRating(ActionEvent actionEvent) {
        this.selectedSort = "RATING";
        displayLeaderboard(this.selectedSort, this.selectedGame);
    }

    public void searchFriends(ActionEvent actionEvent) {
        this.friendSearch = true;
        System.out.println("YES IT WORKING");

        leaderboard = new Leaderboard(selectedSort, selectedGame);
        leaderboard.filterFriends();
        ArrayList<ArrayList<String>> rankingData = leaderboard.getRankings();

        // Recalculate leaderboard positions (1-indexed)
        for (int i = 0; i < rankingData.size(); i++) {
            rankingData.get(i).set(0, String.valueOf(i + 1));
        }

        // Convert the data into an ObservableList for the TableView
        ObservableList<ObservableList<String>> tableData = FXCollections.observableArrayList();
        for (ArrayList<String> row : rankingData) {
            ObservableList<String> observableRow = FXCollections.observableArrayList(row);
            tableData.add(observableRow);
        }

        C4_table.setItems(tableData);
    }

    public void searchAnyone(ActionEvent actionEvent) {
        this.friendSearch = false;
        displayLeaderboard(this.selectedSort, this.selectedGame);
    }

    public void send_search(MouseEvent mouseEvent) {
        String searchText = friend_search.getText();

        if (Objects.equals(searchText, "")) { // if nothing is typed in

            System.out.println("Enter a username to be searched");

        } else { // a username is typed

            System.out.println("Search text: " + searchText);

            // Ensure the leaderboard instance exists
            if (leaderboard != null) {
                // Get searched username
                ArrayList<ArrayList<String>> result = leaderboard.searchPlayer(searchText);

                if (result.isEmpty()) {
                    System.out.println("player not found");
                    return;
                }

                ObservableList<ObservableList<String>> tableData = FXCollections.observableArrayList();
                ObservableList<String> observableRow = FXCollections.observableArrayList(result.get(0));

                tableData.add(observableRow);

                // Update the TableView with the search result
                C4_table.setItems(tableData);
            }
        }
    }

    // filter(), sort(), search() must be here dont delete
    public void filter(ActionEvent actionEvent) {
        // Implement filter functionality as needed.
    }

    public void sort(ActionEvent actionEvent) {
    }

    public void search(ActionEvent actionEvent) {
    }
    public void backButtonClicked() throws IOException {
        AudioManager.mediaPlayer.stop();
        Parent newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gameMenu.fxml")));

        Stage stage = (Stage) search_button.getScene().getWindow();

        stage.setScene(new Scene(newRoot));
        stage.show();
    }
    public void backButtonPressed(){
        backButton.setImage(new Image("home_button_pressed.png"));
    }
    public void backButtonReleased(){
        backButton.setImage(new Image("home_button.png"));
    }
    public void searchPressed(){
        search_button.setImage(new Image("search_button_pressed.png"));
    }
    public void searchReleased(){
        search_button.setImage(new Image("search_button.png"));
    }
    public void muteButtonClicked(){
        if(!AudioManager.isMuted()) {
            muteButton.setImage(new Image("muteButton.png"));
            AudioManager.toggleMute();
        } else {
            muteButton.setImage(new Image("unmuteButton.png"));
            AudioManager.toggleMute();
        }
    }
}
