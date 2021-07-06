package HW_3.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TextArea textArea;

    @FXML
    TextField msgField, loginField;

    @FXML
    HBox msgPanel, authPanel;

    @FXML
    PasswordField passField;

    @FXML
    ListView<String> clientsList;

    private final String YOURSELF_LABEL = " (You)";
    private final int RECOVERABLE_MESSAGE_QUANTITY = 100;
    private boolean authentificated;
    private String nickname;
    private File messageHistoryFile;

    public void setAuthenticated(boolean authentificated) {
        this.authentificated = authentificated;
        authPanel.setVisible(!authentificated);
        authPanel.setManaged(!authentificated);
        msgPanel.setVisible(authentificated);
        msgPanel.setManaged(authentificated);
        clientsList.setVisible(authentificated);
        clientsList.setManaged(authentificated);
        if (!authentificated) {
            nickname = "";
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAuthenticated(false);
        clientsList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String nickname = clientsList.getSelectionModel().getSelectedItem()
                        .replace(YOURSELF_LABEL, "");
                msgField.setText("/w " + nickname + " ");
                msgField.requestFocus();
                msgField.selectEnd();
            }
        });
        linkCallbacks();
    }

    public void sendAuth() {
        Network.sendAuth(loginField.getText(), passField.getText());
        loginField.clear();
        passField.clear();
    }

    public void sendMsg() {
        if (!msgField.getText().equals("")) {
            if (Network.sendMsg(msgField.getText())) {
                msgField.clear();
                msgField.requestFocus();
            }
        }
        msgField.requestFocus();
    }

    public void showAlert(String msg) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK);
            alert.showAndWait();
        });
    }

    public void linkCallbacks() {
        Network.setCallOnException(args -> showAlert(args[0].toString()));

        Network.setCallOnCloseConnection(args -> setAuthenticated(false));

        Network.setCallOnAuthenticated(args -> {
            setAuthenticated(true);
            nickname = args[0].toString();
            int lineNumber = 0;

            if (setMessageHistoryFile().exists()) {
                try (FileReader fr = new FileReader(messageHistoryFile)) {
                    LineNumberReader lnr = new LineNumberReader(fr);
                    while (lnr.readLine() != null) {
                        lineNumber++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try (BufferedReader br = new BufferedReader(new FileReader(messageHistoryFile))) {
                    for (int i = 0; i < lineNumber; i++) {
                        String line = br.readLine();
                        if (i >= lineNumber - RECOVERABLE_MESSAGE_QUANTITY) {
                            textArea.appendText(line + "\n");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    messageHistoryFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Network.setCallOnMsgReceived(args -> {
            String msg = args[0].toString();
            if (msg.startsWith("/")) {
                if (msg.startsWith("/clients ")) {
                    String[] clients = msg.split("\\s", 2)[1].split("\\s");
                    Platform.runLater(() -> {
                        clientsList.getItems().clear();
                        clientsList.getItems().add(nickname + YOURSELF_LABEL);
                        for (String client : clients) {
                            if (!client.equals(nickname)) {
                                clientsList.getItems().add(client);
                            }
                        }
                    });
                }
                if (msg.startsWith("/changenick:")) {
                    if (msg.startsWith("/changenick:error ")) {
                        String errorText = msg.split("\\s", 2)[1];
                        textArea.appendText(errorText + "\n");
                        return;
                    }
                    if (msg.startsWith("/changenick:succeeded ")) {
                        nickname = msg.split("\\s")[1];
                        textArea.appendText("Nickname has been changed\n");
                        messageHistoryFile.renameTo(setMessageHistoryFile());
                    }
                }
            } else {
                textArea.appendText(msg + "\n");
                try (FileWriter fw = new FileWriter(messageHistoryFile, true)) {
                    fw.append(msg + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private File setMessageHistoryFile() {
        messageHistoryFile = new File("client/data/nickname=" + nickname + ".cmh");
        return messageHistoryFile;
    }
}
