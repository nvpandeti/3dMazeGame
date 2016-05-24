/*
 * Nikhil Pandeti
 * Mrs. Gallatin
 * Period 2
 */
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import java.awt.*;

import javax.swing.*;
import javax.imageio.*;

import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.net.URL;
import java.awt.image.*;
/**
 * A class for Sounds
 */
public class Sound implements Runnable
{
	String fileName;
	float masterGain;
	/**
	 * A constructor for Sound
	 * @param fileName file name
	 */
	public Sound(String fileName)
	{
		this.fileName = fileName;
		masterGain = 0f;
	}
	/**
	 * A constructor for Sound
	 * @param fileName file name
	 * @param masterGain volume control
	 */
	public Sound(String fileName, float masterGain)
	{
		this.fileName = fileName;
		this.masterGain = masterGain;
	}
	/**
	 * Plays/mixes the sound
	 */
	public void run() 
	{
		//File soundFile = new File(fileName);
		URL soundFile = this.getClass().getClassLoader().getResource(fileName);
        AudioInputStream audioInputStream = null;
        try 
        {
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AudioFormat audioFormat = audioInputStream.getFormat();
        SourceDataLine line = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try 
        {
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(audioFormat);
            ((FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN)).setValue(masterGain);
        } 
        catch (LineUnavailableException e) 
        {
            e.printStackTrace();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        line.start();
        int nBytesRead = 0;
        byte[] abData = new byte[128000];
        while (nBytesRead != -1) 
        {
            try 
            {
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
            if (nBytesRead >= 0) 
            {
                int nBytesWritten = line.write(abData, 0, nBytesRead);
            }
        }
        line.drain();
        line.close();
	}
}

