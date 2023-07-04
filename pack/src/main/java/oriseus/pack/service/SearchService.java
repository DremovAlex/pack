/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oriseus.pack.service;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import oriseus.pack.modelsViews.*;

/**
 *
 * @author oriseus
 */
public class SearchService {
	
	private SearchService() {}
    
    public static void search(TableView tableView, String searchString, ObservableList<StampView> observableList) {
        for (StampView stampView : observableList) {
            if (stampView.getName().equals(searchString)) {
                tableView.scrollTo(stampView);
                tableView.getSelectionModel().select(stampView);
                return;
            }
        }
    }
    
}
