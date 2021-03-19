package controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import model.Company;
import model.Project;
import model.Stage;
import model.Task;
import model.Worker;
import view.auis.CompanyAUI;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 
 * @author Liyuan
 *
 */
public class IOController {
	private MainController mainController;
	private ArrayList<CompanyAUI> companyAUI;

	/**
	 * 
	 * @param mainController
	 */
	public IOController(MainController mainController) {
		setCompanyAUI(new ArrayList<>());
		if(mainController!=null)
			this.mainController = mainController;
		else
			throw new NullPointerException("mainController is null!");
	}

	/**
	 * Laedt die Company unter filePath und setzt diese
	 * 
	 * @param filePath
	 */
	public void load(String filePath) {
		try {
			FileInputStream fis = new FileInputStream(filePath);
			ObjectInputStream inputStream = new ObjectInputStream(fis);

			mainController.setCompany((Company) inputStream.readObject());

			inputStream.close();
			fis.close();
			companyAUI.forEach(aui-> aui.refreshProjectList());
		} catch(IOException ex) { 
            System.out.println("IOException is caught for path: "+filePath); 
        } catch(ClassNotFoundException ex) { 
            System.out.println("ClassNotFoundException is caught"); 
        } 

	}

	/**
	 * Speichert den aktuellen Company Zustand unter filePath
	 * 
	 * @param filePath
	 */
	public void save(String filePath) {
		try {
			FileOutputStream fos = new FileOutputStream(filePath);
			ObjectOutputStream outputStream = new ObjectOutputStream(fos);

			outputStream.writeObject(mainController.getCompany());

			outputStream.close();
			fos.close();
		} catch (IOException ex) {
			System.out.println("IOException is caught");
		}

	}

	/**
	 * Schreibt den Test auf die übergebene Grafik
	 * 
	 * @param graphic
	 * @param text
	 * @param xCoord
	 * @param yCoord
	 */
	private void drawString(Graphics2D graphic, String text, int xCoord, int yCoord) {
		for (String line : text.split("\n"))
			graphic.drawString(line, xCoord, yCoord += graphic.getFontMetrics().getHeight());
	}

	/**
	 * Renders the PDF export
	 * 
	 * @param projects
	 * @param filePath
	 */
	public void createReport(ArrayList<Project> projects, String filePath) {
		ArrayList<BufferedImage> images = new ArrayList<>();

		int dpmm = 4;
		int dinA4Height = 210;
		int dinA4Width = 297;
		int totalWidth = dinA4Width * dpmm;
		int totalHeight = dinA4Height * dpmm;
		int col = 5;

		for (Project project : projects) {
			// draw the Table
			BufferedImage image = new BufferedImage(totalWidth, totalHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D currentBoardImage = (Graphics2D) image.createGraphics();

			// set the background to white
			currentBoardImage.setColor(Color.WHITE);
			currentBoardImage.fillRect(0, 0, totalWidth, totalHeight);

			currentBoardImage.setColor(Color.black);
			currentBoardImage.setStroke(new BasicStroke(2));
			currentBoardImage.drawRect(0, 0, totalWidth, totalHeight);
			currentBoardImage.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			currentBoardImage.drawString("Program:" + project.getName(), 0, 18);

			int upperside = 21;
			for (int i = 0; i < col; i++) {
				currentBoardImage.drawRect(i * totalWidth / col, upperside, totalWidth / col,
						(int) (totalHeight * 0.8));
			}
			currentBoardImage.drawLine(totalWidth / col, (int) (totalHeight * 0.4 + upperside), 4 * totalWidth / col,
					(int) (totalHeight * 0.4 + upperside));

			// For all tasks in tasks draw in stage on currentBoardImage
			int fontSize = 12;
			int offset = 15;
			currentBoardImage.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
			String oPut = "";// to save Title and name of a task

			// write Stage of new and done
			Stage[] stagesUP1 = { Stage.NEW, Stage.DONE };
			String[] headersUP1 = { "NEW", "DONE" };
			for (int i = 0; i < stagesUP1.length; i++) {
				ArrayList<Task> currentTasks = mainController.getProjectController().getProjectTasksInStage(project,
						stagesUP1[i]);
				oPut = headersUP1[i] + ":" + "\n";
				for (Task task : currentTasks) {
					oPut = oPut + task.getTitle() + "\n";
					// if(i!=0 && i!=4)
					// oPut=oPut+task.getCurrentWorker().getFirstname()+"
					// "+task.getCurrentWorker().getLastname()+"\n";
				}
				if (i == 0)
					drawString(currentBoardImage, oPut, 2, upperside + fontSize - offset);
				else
					drawString(currentBoardImage, oPut, 4 * totalWidth / col + 2, upperside + fontSize - offset);
				oPut = "";
			}

			// write stage of Analyse, implementation and test
			Stage[] stagesUP2 = { Stage.ANALYSE, Stage.IMPLEMENTATION, Stage.TEST };
			String[] headersUP2 = { "ANALYSE", "IMPLEMENTATION", "TEST" };

			for (int i = 0; i < stagesUP2.length; i++) {
				ArrayList<Task> currentTasks = mainController.getProjectController().getProjectTasksInStage(project,
						stagesUP2[i]);
				currentBoardImage.drawString(headersUP2[i], 2 + (i + 1) * totalWidth / col, upperside + fontSize);
				int rows = 2;
				int offset2 = 6;
				for (Task task : currentTasks) {
					currentBoardImage.drawString(task.getTitle() + ":", 2 + (i + 1) * totalWidth / col,
							upperside + fontSize * rows + offset2 * (rows - 1));
					rows++;
					currentBoardImage.setColor(Color.blue);
					currentBoardImage.drawString(
							task.getCurrentWorker().getFirstname() + " " + task.getCurrentWorker().getLastname(),
							2 + (i + 1) * totalWidth / col, upperside + fontSize * rows + offset2 * (rows - 1));
					rows++;
					currentBoardImage.setColor(Color.black);
				}
			}

			// write stage of ANALYSEDONE,IMPLEMENTATIONDONE,TESTDON
			Stage[] stagesDONE = { Stage.ANALYSEDONE, Stage.IMPLEMENTATIONDONE, Stage.TESTDONE };
			String[] headersDONE = { "ANALYSEDONE", "IMPLEMENTATIONDONE", "TESTDONE" };
			for (int i = 0; i < stagesDONE.length; i++) {
				ArrayList<Task> currentTasks = mainController.getProjectController().getProjectTasksInStage(project,
						stagesDONE[i]);
				oPut = headersDONE[i] + ":" + "\n";
				for (Task task : currentTasks) {
					oPut = oPut + task.getTitle() + "\n";
				}
				drawString(currentBoardImage, oPut, (i + 1) * totalWidth / col + 2,
						(int) (totalHeight * 0.4 + upperside + fontSize - offset));
				oPut = "";
			}

			// show unassigned workers
			ArrayList<Worker> unassignedworkers = mainController.getTeamController()
					.getUnassignedTaskWorkersFromProject(project);
			oPut = "nicht zugewiesen:" + "\n";
			for (Worker worker : unassignedworkers)
				oPut = oPut + worker.getFirstname() + " " + worker.getLastname() + "   ";
			// drawString(currentBoardImage,oPut,0, (int) (totalHeight*0.8+31));
			currentBoardImage.drawString(oPut, 2, (int) (totalHeight * 0.8 + upperside + fontSize));

			currentBoardImage.dispose();
			images.add(image);
		}

		// write the images to a pdf file

		try {
			Document document = new Document();
			document.setPageSize(PageSize.A4.rotate());

			PdfWriter.getInstance(document, new FileOutputStream(filePath));
			document.open();
			float marginLeft = 10; // in pdf units
			float marginRight = 10;
			float marginTop = 10;
			float marginBottom = 10;
			final float pdfUnitsPerMM = 1 / (float) 25.4 * 72;
			document.setMargins(marginLeft, marginRight, marginTop, marginBottom);
			for (BufferedImage image : images) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(image, "png", baos);
				com.itextpdf.text.Image iTextImage = com.itextpdf.text.Image.getInstance(baos.toByteArray());
				iTextImage.scaleAbsolute(dinA4Width * pdfUnitsPerMM - marginLeft - marginRight,
						dinA4Height * pdfUnitsPerMM - marginTop - marginBottom);
				document.newPage();
				document.add(iTextImage);
			}
			document.close();
		} catch (DocumentException e) {

		} catch (FileNotFoundException e) {

		} catch (MalformedURLException e) {

		} catch (IOException e) {
		}
	}

	/**
	 * @return the companyAUI
	 */
	public ArrayList<CompanyAUI> getCompanyAUI() {
		return companyAUI;
	}

	/**
	 * @param companyAUI the companyAUI to set
	 */
	public void setCompanyAUI(ArrayList<CompanyAUI> companyAUI) {
		this.companyAUI = companyAUI;
	}

}
