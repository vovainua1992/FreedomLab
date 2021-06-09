package com.example.springmvc.utils;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.process.ImageProcessor;

import java.nio.file.Path;

public class ImageEditorWithImageJ implements ImageEditor {

    public String duplicateAndCrop(Path path, int posX, int posY, int width) {
        String name = "small" + path.getFileName().toString();
        String resultPath = path.getParent().toString()+"\\"+name;
        ImagePlus imp = IJ.openImage(path.toString());
        ImageProcessor ip = imp.getProcessor();
        Roi roi = new Roi(posX, posY, width, width); // x, y, width, height of the rectangle
        ip.setRoi(roi);
        ip = ip.crop();
        imp = new ImagePlus("small" + name, ip);
        IJ.save(imp, resultPath);
        return resultPath;
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
