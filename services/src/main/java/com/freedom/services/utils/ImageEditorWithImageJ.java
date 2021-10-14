package com.freedom.services.utils;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.process.ImageProcessor;

import java.io.IOException;
import java.nio.file.*;

public class ImageEditorWithImageJ implements ImageEditor {

    @Override
    public void cropImage(Path path,double scalar, int posX, int posY, int size) {
        ImagePlus imp = IJ.openImage(path.toString());
        ImageProcessor ip = imp.getProcessor();
        double sizeScale = size*scalar;
        Roi roi = new Roi(posX*scalar, posY*scalar, sizeScale, sizeScale); // x, y, width, height of the rectangle
        ip.setRoi(roi);
        ip = ip.crop();
        imp = new ImagePlus(path.getFileName().toString(), ip);
        IJ.save(imp,path.toString());
    }

    public void mirrorHorizontal(Path path) {
        ImagePlus imp = IJ.openImage(path.toString());
        ImageProcessor ip = imp.getProcessor();
        ip.flipHorizontal();
        IJ.save(imp, path.toString());
    }

    public void mirrorVertical(Path path) {
        ImagePlus imp = IJ.openImage(path.toString());
        ImageProcessor ip = imp.getProcessor();
        ip.flipVertical();
        IJ.save(imp, path.toString());
    }

    public void rotateRight(Path path){
        ImagePlus imp = IJ.openImage(path.toString());
        ImageProcessor ip = imp.getProcessor();
        imp.setProcessor(ip.rotateRight());
        IJ.save(imp, path.toString());
    }

    public void rotateLeft(Path path){
        ImagePlus imp = IJ.openImage(path.toString());
        ImageProcessor ip = imp.getProcessor();
        imp.setProcessor(ip.rotateLeft());
        IJ.save(imp, path.toString());
    }

}
