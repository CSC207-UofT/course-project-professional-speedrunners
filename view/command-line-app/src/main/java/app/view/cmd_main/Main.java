package app.view.cmd_main;

import app.adapter.controller.GenericController;
import app.adapter.db.item_db.ItemDbHashmap;
import app.adapter.id_generator.uuid.UuidGen;
import app.adapter.presenter.GenericPresenter;

public class Main {
    public static void main(String[] args) {
        View view = new View();
        GenericController controller = new GenericController(new ItemDbHashmap(),
                new UuidGen(),
                new GenericPresenter(),
                view);
        /*
        GenericController.builder()
        .setItemDb(new ItemDbHashmap())
        .setIdGen(new UuidGen())
        .setPresenter(new GenericPresenter())
        .setView(view)
        .setViewCreateItem(view)
        .setViewFindItem(view)
        .setViewRemoveItem(view)
        .build();
        */
        controller.run();
    }
}
