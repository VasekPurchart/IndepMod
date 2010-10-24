package cz.cvut.indepmod.uc.frames.vertexInfo;

import cz.cvut.promod.services.ModelerSession;

import javax.swing.*;
import java.awt.*;

import com.jidesoft.grid.PropertyTable;

/**
 * UseCase plugin - SI2/3 school project
 * User: Alena Varkockova
 * User: Viktor Bohuslav Bohdal
 *
 * The View component for the Info dockable frame. 
 */
public class VertexInfoView extends JPanel {

    protected final PropertyTable table = ModelerSession.getComponentFactoryService().createPropertyTable();    

    public VertexInfoView(){
        initLayout();
    }

    private void initLayout() {
        setLayout(new BorderLayout());

        add(ModelerSession.getComponentFactoryService().createScrollPane(table));
    }

}
