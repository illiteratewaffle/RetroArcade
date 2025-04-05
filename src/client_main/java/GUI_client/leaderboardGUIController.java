package GUI_client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.util.Callback;
import leaderboard.Leaderboard;

import java.util.ArrayList;

public class leaderboardGUIController {

    private static final String GAME_CHECKERS = "CHECKERS";
    private static final String GAME_TTT = "TTT";
    private static final String GAME_C4 = "C4";

    private String selectedSort = "RATING"; // default sort if needed
    private String selectedGame;

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
    public TextField friend_search;   // action: search

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
    @FXML
    public void initialize() {
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

        // apply the common style
        column.setCellFactory(new Callback<TableColumn<ObservableList<String>, String>, TableCell<ObservableList<String>, String>>() {
            @Override
            public TableCell<ObservableList<String>, String> call(TableColumn<ObservableList<String>, String> param) {
                return new TableCell<ObservableList<String>, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item);
                            setStyle("-fx-background-color: transparent; -fx-text-fill: #00d8ff; -fx-font-weight: bold;");
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
        Leaderboard leaderboard = new Leaderboard(selectedSort, selectedGame);
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

    public void filter(ActionEvent actionEvent) {
        // Implement filter functionality as needed.
    }

    public void send_search(MouseEvent mouseEvent) {
        // Implement search button click action.
    }

    public void search(ActionEvent actionEvent) {
        // Implement search field action.
    }

    public void sort(ActionEvent actionEvent) {
        // Implement sorting functionality as needed.
    }

    public void order(ActionEvent actionEvent) {
        // Implement sort order functionality as needed.
    }
}
