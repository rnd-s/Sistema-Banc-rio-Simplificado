import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Button;

public class App extends Application {
    private void Click_Cadastro( Stage stage) {
       VBox vBox = new VBox();
       vBox.setAlignment(Pos.CENTER);

      Scene cadastroScene = new Scene(vBox, 1080, 720);
      stage.setScene(cadastroScene);
      
     
       stage.show();       
      

    }

    private VBox Movimentar(VBox vBox) {
        VBox Vbox1 = new VBox(15); 
        
        Text text = new Text("Bem Vindo ao Banco RNB");
        Text descricao = new Text("Um banco simplificado para o seu dia a dia");
        Button botao = new Button("Cadastro");   
        botao.setFont(Font.font("System", FontWeight.BOLD, 16));     
        botao.setTranslateY(20);
        //botao.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000; -fx-padding: 10 20; -fx-background-radius: 5;");
        botao.getStyleClass().add("botao-cadastro");
        botao.setOnAction(event -> Click_Cadastro((Stage) botao.getScene().getWindow()));
      

        text.setFont(Font.font("System", FontWeight.BOLD, 36)); 
        text.setFill(Color.web("#ffffff"));
        text.setTextAlignment(TextAlignment.CENTER);
   
       
        descricao.setFill(Color.web("#B0BEC5"));
        descricao.setFont(Font.font("System", 18));
        descricao.setTextAlignment(TextAlignment.CENTER);
        descricao.setWrappingWidth(700);
 
        Vbox1.setAlignment(Pos.CENTER);
       
        Vbox1.setStyle("-fx-background-color: #4c257b; -fx-padding: 40; -fx-background-radius: 10;");

        Vbox1.getChildren().addAll(text, descricao , botao);

        Vbox1.setPrefWidth(700);
        Vbox1.setPrefHeight(250);
        Vbox1.setMaxWidth(800);
        Vbox1.setMaxHeight(400);

        vBox.setPadding(new Insets(20));
        vBox.setAlignment(Pos.CENTER);
        
        vBox.setStyle("-fx-background-color:#e0f2ff;"); 
        vBox.getChildren().add(Vbox1);
 
        return vBox;
    }

    public void start(Stage primaryStage) {
        VBox vBox = new VBox();
        
       
        
        Scene cena = new Scene(Movimentar(vBox));
        cena.getStylesheets().add(getClass().getResource("/Styles/main.css").toExternalForm());
        primaryStage.setTitle("Banco Simplificado");
        primaryStage.setScene(cena);
        primaryStage.setWidth(1080);
        primaryStage.setHeight(720);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
