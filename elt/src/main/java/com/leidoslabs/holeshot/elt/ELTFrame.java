/*
 * Licensed to Leidos, Inc. under one or more contributor license agreements.  
 * See the NOTICE file distributed with this work for additional information regarding copyright ownership.
 * Leidos, Inc. licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.leidoslabs.holeshot.elt;

import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.joml.Vector2d;
import org.locationtech.jts.geom.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leidoslabs.holeshot.elt.coord.ImageWorld;
import com.leidoslabs.holeshot.elt.coord.ImageWorldMouseListener;
import com.leidoslabs.holeshot.elt.observations.Observation;
import com.leidoslabs.holeshot.elt.tileserver.TileserverUrlBuilder;
import com.leidoslabs.holeshot.imaging.coord.GeointCoordinate;

@SuppressWarnings("serial")
/**
 * Representation of a frame of the ELTCanvas. Contains and managers ELTCanvas. 
 */
public class ELTFrame extends Shell {
   private static final Logger LOGGER = LoggerFactory.getLogger(ELTFrame.class);
   private ELTCanvas glCanvas;
   private Label locationLabel;
   protected BrowserFrame browserFrame;
   private static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.0000#");

   /**
    * Construct ELTFrame. Initialize ELTCanvas,
    * @param display
    * @param imageMetadataURL
    * @param appId
    * @param isDecorated
    * @param progressiveRender
    * @throws IOException
    * @throws InterruptedException
    * @throws ExecutionException
    */
   public ELTFrame(Display display, URL imageMetadataURL, String appId, boolean isDecorated, boolean progressiveRender) throws IOException, InterruptedException, ExecutionException {
      super(display, isDecorated ? SWT.SHELL_TRIM : SWT.NO_TRIM);

      setText(getFrameName(imageMetadataURL));

      // Allows for transparent controls
//      setBackground(black);
//      setBackgroundMode(SWT.INHERIT_FORCE);

      setLayout(new GridLayout(1,false));

      // set the JFrame title

      glCanvas = new ELTCanvas(this, imageMetadataURL, appId, progressiveRender);
      glCanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
      glCanvas.setLayout(new GridLayout(1, false));


      if (isDecorated) {
         locationLabel = new Label(glCanvas, SWT.NONE);
         locationLabel.setBackground(display.getSystemColor(SWT.COLOR_TRANSPARENT));
         GridData labelGridData = new GridData(SWT.RIGHT, SWT.BOTTOM, true, true, 1, 1);
         // labelGridData.minimumWidth = 150;

         FontData[] fontData = locationLabel.getFont().getFontData();
         fontData[0].setHeight(12);
         locationLabel.setFont(new Font(display, fontData[0]));
         locationLabel.setLayoutData(labelGridData);
         locationLabel.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
         locationLabel.setForeground(display.getSystemColor(SWT.COLOR_GRAY));

         // Set the text to something that takes up the maximum size for this field
         locationLabel.setText(String.format("lat: %s lon: %s", DECIMAL_FORMAT.format(-179.99999), DECIMAL_FORMAT.format(-90.99999)));

         locationLabel.setLayoutData(labelGridData);

         glCanvas.addELTMouseListener(new ImageWorldMouseListener() {
            @Override
            public void mouseMoved(GeointCoordinate<?> eltCoordinate) {
               Coordinate geodeticCoord = eltCoordinate.getGeodeticCoordinate();
               locationLabel.setText(String.format("lat: %s lon: %s", DECIMAL_FORMAT.format(geodeticCoord.y), DECIMAL_FORMAT.format(geodeticCoord.x)));
            }
         });
      }

      // center the JFrame on the screen
      centerWindow(this);
//      this.pack();

      setSize(ImageWorld.DEFAULT_WIDTH, ImageWorld.DEFAULT_HEIGHT);
//    setMinimumSize(ImageWorld.DEFAULT_WIDTH, ImageWorld.DEFAULT_HEIGHT);

      setImage(new Image(display, ELTFrame.class.getClassLoader().getResourceAsStream("com/leidoslabs/holeshot/elt/images/icon.png")));
      setText(String.format("ELT - %s", glCanvas.getImage().getTilePyramidDescriptor().getName()));
//      this.setIconImage(ELTSystemTray.createImage("images/icon.png", "ELT"));


      this.addKeyListener(new KeyAdapter() {
      @Override
         public void keyPressed(KeyEvent e) {
         if (((e.stateMask & (SWT.SHIFT | SWT.CONTROL)) == ((SWT.SHIFT | SWT.CONTROL))) && e.keyCode == 'T') {
            //new TileFetcher(ELTFrame.this, glCanvas.getTopTile());
         }
      }});

      browserFrame = new BrowserFrame(this);
      browserFrame.open();
      browserFrame.addListener(SWT.Close, e -> {
         e.doit = false;
         browserFrame.setVisible(false);
      });
      browserFrame.setVisible(false);

      this.open();
   }

   @Override
   protected void checkSubclass() {
   }

   @Override
   public void update() {
      browserFrame.close();
      super.update();
   }

   public String getAppId() {
	   return getELTCanvas().getAppId();
   }
   public ELTCanvas getELTCanvas() {
      return glCanvas;
   }

   public void centerWindow(Shell frame) {
      java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Point frameSize = frame.getSize();

      if (frameSize.x > screenSize.width)
         frameSize.x = screenSize.width;
      if (frameSize.y > screenSize.height)
         frameSize.y = screenSize.height;

      frame.setLocation((screenSize.width - frameSize.x) >> 1,
            (screenSize.height - frameSize.y) >> 1);
   }

   private static String getFrameName(URL imageMetadataURL) {
      TileserverUrlBuilder urlBuilder = new TileserverUrlBuilder(imageMetadataURL);

      return String.format("%s/%s", urlBuilder.getCollectionID(), urlBuilder.getTimestamp());
   }

   public void render(double interpolation) {
      glCanvas.updateAnimation(interpolation);
      glCanvas.renderLoop();
      glCanvas.swapBuffers();
   }

   public void updateAnimation() {
      glCanvas.updateAnimation();
   }

   public void showEditor(Observation obs, Vector2d clickPoint) {
      browserFrame.setVisible(false);
      browserFrame.viewObservation(obs, new Point((int) clickPoint.x, (int) clickPoint.y));
      browserFrame.setVisible(true);
   }

   public void hideEditor() { browserFrame.close(); }
}
