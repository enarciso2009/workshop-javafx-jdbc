package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Seller;
import model.services.SellerService;

public class SellerListController implements Initializable, DataChangeListener {

	private SellerService service;

	@FXML
	private TableView<Seller> tableViewSeller; // referencias para nossa tela grafica

	@FXML
	private TableColumn<Seller, Integer> tableColumnId;

	@FXML
	private TableColumn<Seller, String> tableColumnName;

	@FXML
	private TableColumn<Seller, Seller> tableColumnEDIT;

	@FXML
	private TableColumn<Seller, Seller> tableColumnREMOVE;

	@FXML
	private Button btNew;

	private ObservableList<Seller> obsList;

	@FXML

	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Seller obj = new Seller(); // o departamento esta vazio quando o bot�o � clicado
		createDialogForm(obj, "/gui/SellerForm.fxml", parentStage); // incluimos o obj que na chamada do
																		// createDialogForm
	}

	public void setSellerService(SellerService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id")); // este comando serve para preencher as
																				// colunas do programa grafico
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("Name")); // este comando serve para preencher as
																					// colunas do programa grafico

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewSeller.prefHeightProperty().bind(stage.heightProperty()); // este comandos servem para a grade
																				// acompanhar o menu principal
	}

	public void updateTableView() { // este comando sera responsavel por criar a listagem para o obsList
		if (service == null) {
			throw new IllegalStateException("O service estava null");
		}
		List<Seller> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewSeller.setItems(obsList);
		initEditButtons();
		initRemoveButtons();

	}

	private void createDialogForm(Seller obj, String absoluteName, Stage parentStage) { // fun��o para carregar o
																							// formulario para o usuario
																							// preencher um novo
																							// vendedor.
//		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName)); // carregando a view
//			Pane pane = loader.load();
//
//			SellerFormController controller = loader.getController(); // pegamos o controlador da tela que acabos de
//																			// carregar do comando acima
//			controller.setSeller(obj);
//			controller.setSellerService(new SellerService());
//			controller.subscribeDataChangeListener(this); // colocamos ele aqui para ficar observando se teve alguma
//															// altera��o na lista para fazer o update
//			controller.updateFormData();
//
//			Stage dialogStage = new Stage(); // criando uma nova variavel
//			dialogStage.setTitle("Enter Seller data"); // colocando o titulo da janela
//			dialogStage.setScene(new Scene(pane)); // para trazer uma nova cena trazendo o pene como raiz
//			dialogStage.setResizable(false); // false a janela n�o pode ser redimencionada
//			dialogStage.initOwner(parentStage); // colocando quem � o pai desta janela
//			dialogStage.initModality(Modality.WINDOW_MODAL); // este comando que fala que o a janela esta travada
//			dialogStage.showAndWait();
//
//		} catch (IOException e) {
//			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
//		}

	}

	@Override
	public void onDataChenged() { // este comando avisa que a table foi alterada
		updateTableView(); // o comando chama o update para atualizar a tabela

	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/SellerForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Seller obj) {
	Optional <ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete");
		
		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				updateTableView();
			}
			catch (DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
		
			
		}
		
	}

}










