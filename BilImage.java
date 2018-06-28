import java.lang.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.table.*;
import java.io.*;
import java.util.*;
//import com.sun.image.codec.jpeg.*;
import javax.swing.filechooser.FileFilter;
import java.util.Hashtable;
import java.util.Enumeration;
import java.nio.*;
import java.nio.channels.*;

class BilImage extends JFrame implements ActionListener
{
	JMenuItem miOpen;
	JMenuItem miSave;
	JMenuItem miExit;
	JMenuItem miAbout;
	JMenu mnHelp;
	JMenuItem miContrast;
	JMenuItem mifcc;
	JMenuItem miCross;
	JMenu mnEnhance;
	JMenu mnTemp;
	JMenu mnTools;
	JMenuItem miBandSelection;
	JMenuItem miTemporal;
	JMenuItem miRatio;
	JMenuItem miSaveOut;
	JMenu mnClass;
	JMenuItem miPCM;
	JMenuItem miNWE;
	JMenuItem miNE;
	JMenuItem miManual;
	JMenuItem zin;
	JMenuItem zout;
	JMenuItem miClickSelection;
	JMenuItem miReset;

	JLabel l1;
	AboutDialog dialog = null;
	Font f;
	Color co;
	Container con = getContentPane();

	String fname;
	String ImageName;
	BufferedImage img;
	Container ContentPane = getContentPane();
	DemoPanel pane;
	static int p, q, r;

	public static void main(String s[])
	{

		BilImage frame = new BilImage();
		frame.setVisible(true);
		frame.setSize(700, 500);
		frame.setTitle("SMIC - Temporal Data Processing Module");
		frame.show();

	}

	BilImage()
	{

		JMenuBar mb = new JMenuBar();
		JMenu mnfile = new JMenu("File");
		mnEnhance = new JMenu("Enhance");
		mnTemp = new JMenu("Temporal Data");
		mnTools = new JMenu("Tools");
		miBandSelection = new JMenuItem("Band Selection");
		miTemporal = new JMenuItem("Load Temporal Image");
		miRatio = new JMenuItem("Select Band Ratio");
		miSaveOut = new JMenuItem("Save Output Files");
		miManual = new JMenuItem("Enter Values Manually");
		mnClass = new JMenu("Classifiers");
		miPCM = new JMenuItem("PCM Classifier");
		miNWE = new JMenuItem("Noise Clustering Without Entropy");
		miNE = new JMenuItem("Noise Clustering With Entropy");
		zin = new JMenuItem("Zoom In");
		zout = new JMenuItem("Zoom Out");
		miClickSelection = new JMenuItem("Click Selection");
		miReset = new JMenuItem("Reset Clicks");

		mnClass.add(miPCM);
		mnClass.add(miNWE);
		mnClass.add(miNE);
		mnTemp.add(miTemporal);
		mnTemp.add(miSaveOut);
		mnTemp.add(miManual);
		mnTools.add(miRatio);
		mnTools.add(miBandSelection);
		mnTools.add(miClickSelection);
		mnTools.add(miReset);

		mnEnhance.setEnabled(false);
		mnHelp = new JMenu("Help");
		miOpen = new JMenuItem("Open");
		miSave = new JMenuItem("Save");
		miExit = new JMenuItem("Exit");
		miAbout = new JMenuItem("About");
		miContrast = new JMenuItem("Contrast");
		mifcc = new JMenuItem("FCC");
		miCross = new JMenuItem("Cross Check");

		mnfile.add(miOpen);
		mnfile.add(miSave);
		mnfile.addSeparator();
		mnfile.add(miExit);
		mnEnhance.add(miContrast);
		mnEnhance.add(mifcc);
		mnEnhance.add(zin);
		mnEnhance.add(zout);
		//mnEnhance.add(miCross);
		mnHelp.add(miAbout);
		mb.add(mnfile);
		mb.add(mnEnhance);
		mb.add(mnHelp);
		mb.add(mnTemp);
		mb.add(mnClass);
		mb.add(mnTools);
		setJMenuBar(mb);

		//con.setBackground(Color.PINK);
		f = new Font("Palatino Linotype", Font.ITALIC, 21);

		miOpen.addActionListener(this);
		miSave.addActionListener(this);
		miExit.addActionListener(this);
		miAbout.addActionListener(this);
		miContrast.addActionListener(this);
		mifcc.addActionListener(this);
		miCross.addActionListener(this);
		miTemporal.addActionListener(this);
		miRatio.addActionListener(this);
		miSaveOut.addActionListener(this);
		miPCM.addActionListener(this);
		miNWE.addActionListener(this);
		miNE.addActionListener(this);
		miManual.addActionListener(this);
		miBandSelection.addActionListener(this);
		miClickSelection.addActionListener(this);
		miReset.addActionListener(this);
		zin.addActionListener(this);
		zout.addActionListener(this);


		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				System.exit(0);
			}
		});

	}

	JButton okBut;
	JTextField jtBand, jtRow, jtCol;
	JFrame cross;

	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();

		if (source == miOpen)
		{
			DemoPanel.count = 0;
			//DemoPanel.ratio = 0;
			JFileChooser chooser = new JFileChooser();
			int r1 = chooser.showOpenDialog(this);
			if (r1 == chooser.APPROVE_OPTION)
			{
				File file = chooser.getSelectedFile();
				String name = file.getName();
				ReadImageData Rid1 = new ReadImageData(file);
				this.ImageName = name;
				picture Picture1 = new picture();
				picture.setimage(name);
				MyHdrFile.browse(file);

				String s;
				try
				{
					p = q = r = 1;
					img = Picture1.getpicdata(p, q, r);
				}
				catch (Exception e)
				{
					System.out.println("File Open Exception : " + e);
				}
				//BandRatio br = new BandRatio(file.getPath() + "\\" + file.getName());
				DemoPanel pane = new DemoPanel(img);
				DemoPanel.setImageName(file.getPath());
				ContentPane.removeAll();
				ContentPane.add(new JScrollPane(pane));
				mnEnhance.setEnabled(true);
				System.gc();
				validate();
		//		enh = 0;                              //for enhancenent
			}

		}

		if(source == miTemporal)
		{
			System.out.println("Temporal Clicked");
			if(DemoPanel.count == 0)
			{
				JOptionPane.showMessageDialog(null, "Please Choose The Master Image First From File->Open","Message", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			JFileChooser chooser = new JFileChooser();
			int r = chooser.showOpenDialog(this);
			if (r == chooser.APPROVE_OPTION)
			{
				File file = chooser.getSelectedFile();
				ReadImageData Rid1 = new ReadImageData();
				Rid1.read(file);
				int r1, c1, b1;
				r1 = Rid1.rows();
				c1 = Rid1.columns();
				b1 = Rid1.bands();
				File f2 = new File(DemoPanel.ImageName[0]);
				ReadImageData Rid2 = new ReadImageData();
				Rid2.read(f2);

				if(r1 != Rid2.rows() || c1 != Rid2.columns() || b1 != Rid2.bands())
				{
					JOptionPane.showMessageDialog(null, "Invalid Image!!! Please Select Image That Has The Same Number Of Rows, Column and Bands As That Of Master Image","Message", JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				DemoPanel.setImageName(file.getPath());

			}
		}

		if(source == miRatio)
		{
			new RatioSelection();
		}

		if(source == miBandSelection)
		{
			new BandSelection();
		}

		if(source == miClickSelection)
		{
			new ClickSelection();
		}

		if(source == miReset)
		{
			DemoPanel.clickCount = 0;
			JOptionPane.showMessageDialog(null, "Click Count Has Been Reset To 0", "Message", JOptionPane.INFORMATION_MESSAGE);
		}

		if(source == miSaveOut)
		{
			File fn;
			FileInputStream f1;
			FileOutputStream fout;
			FileChannel fchan, fchanout;
			int i, j, k, tempWidth = DemoPanel.width;
			MappedByteBuffer mBuf;
			long lstart;
			byte b1, b2;
			int p1, p2;
			int nob = 1;
			int tempX = DemoPanel.x;
			int bvalues[] = new int[DemoPanel.bands];
			if(DemoPanel.shiftFlag == 16)
			{
				tempWidth = DemoPanel.width * 2;
				nob = 2;
				tempX = DemoPanel.x * 2;
			}

			byte minB[] = new byte[tempWidth];
			byte maxB[] = new byte[tempWidth];
			byte finalVal[] = new byte[DemoPanel.width];
			int minVal, maxVal, minIndex, maxIndex;
			double calcRatio;

			try
			{
				for(i=0; i<DemoPanel.count; i++)
				{
					System.out.println("Saving Output File : " + i);
					fn = new File(DemoPanel.ImageName[i]);
					f1 = new FileInputStream(fn);
					fchan = f1.getChannel();
					fout = new FileOutputStream(DemoPanel.ImageName[i] + "Output" + i);

					if(DemoPanel.bandType == 0)
					{
						for(j=0; j<DemoPanel.height; j++)
						{

							////////////////////
							for(k=0; k<DemoPanel.bands; k++)
							{
								lstart = (DemoPanel.y * tempWidth * DemoPanel.bands) + (k * tempWidth) + tempX;
								mBuf = fchan.map(FileChannel.MapMode.READ_ONLY, lstart, nob);
								if(DemoPanel.shiftFlag == 8)
								{
									b1 = mBuf.get();
									bvalues[k] = (b1<<24) >>> 24;
								}
								else
								{
									b1 = mBuf.get();
									b2 = mBuf.get();
									p1 = b1 << 24;
									p2 = (b2 <<24 ) >>> 8;
									bvalues[k] = p1 | p2;
									bvalues[k] = bvalues[k] >>> 16;

								}
								//System.out.println("Value stored at Band No. " + i + ", " + bvalues[i]);
							}

							minIndex = maxIndex = 0;
							minVal = maxVal = bvalues[0];
							for(k=1; k<DemoPanel.bands; k++)
							{
								if(bvalues[k] > maxVal)
								{
									maxVal = bvalues[k];
									maxIndex = k;
								}
								if(bvalues[k] < minVal)
								{
									minVal = bvalues[k];
									minIndex = k;
								}
							}


							lstart = (j * DemoPanel.bands * tempWidth) + minIndex * tempWidth;
							mBuf = fchan.map(FileChannel.MapMode.READ_ONLY, lstart, tempWidth);
							mBuf.get(minB);

							lstart = (j * DemoPanel.bands * tempWidth) + maxIndex * tempWidth;
							mBuf = fchan.map(FileChannel.MapMode.READ_ONLY, lstart, tempWidth);
							mBuf.get(maxB);

							for(k=0; k<DemoPanel.width; k++)
							{
								if(DemoPanel.shiftFlag == 8)
								{
									minVal = (minB[k] << 24) >>> 24;
									maxVal = (maxB[k] << 24) >>> 24;
								}
								else
								{
									p1 = minB[2*k] << 24;
									p2 = (minB[2*k+1] << 24) >>> 8;
									minVal = p1 | p2;
									minVal = minVal >>> 16;

									p1 = maxB[2*k] << 24;
									p2 = (maxB[2*k+1] << 24) >>> 8;
									maxVal = p1 | p2;
									maxVal = maxVal >>> 16;
								}

								if(DemoPanel.ratioType == 0)
								{
									calcRatio = (double)minVal / (double)maxVal;
								}
								else if(DemoPanel.ratioType == 1)
								{
									calcRatio = (double)(maxVal - minVal) / (double)(maxVal + minVal);
								}
								else
								{
									double d = ((double)(maxVal - minVal) / (double)(maxVal + minVal)) + 0.5;
									if(d < 0.0)
										d = 0.0;
									calcRatio = Math.sqrt(d);
								}
								if(calcRatio < 0.0)
									calcRatio = 0.0;
								if(calcRatio > 1.0)
									calcRatio = 1.0;
								finalVal[k] = (byte)(calcRatio * 255.0);
								//fchanout.write(finalVal[k]);
							}
							//System.out.println("Before Write");
							fout.write(finalVal);
							//fchanout.write(finalVal);
						}
					}
					else
					{
						for(j=0; j<DemoPanel.height; j++)
						{
							lstart = (j * DemoPanel.bands * tempWidth) + DemoPanel.minIndex * tempWidth;
							mBuf = fchan.map(FileChannel.MapMode.READ_ONLY, lstart, tempWidth);
							mBuf.get(minB);

							lstart = (j * DemoPanel.bands * tempWidth) + DemoPanel.maxIndex * tempWidth;
							mBuf = fchan.map(FileChannel.MapMode.READ_ONLY, lstart, tempWidth);
							mBuf.get(maxB);

							for(k=0; k<DemoPanel.width; k++)
							{
								if(DemoPanel.shiftFlag == 8)
								{
									minVal = (minB[k] << 24) >>> 24;
									maxVal = (maxB[k] << 24) >>> 24;
								}
								else
								{
									p1 = minB[2*k] << 24;
									p2 = (minB[2*k+1] << 24) >>> 8;
									minVal = p1 | p2;
									minVal = minVal >>> 16;

									p1 = maxB[2*k] << 24;
									p2 = (maxB[2*k+1] << 24) >>> 8;
									maxVal = p1 | p2;
									maxVal = maxVal >>> 16;
								}

								if(DemoPanel.ratioType == 0)
								{
									calcRatio = (double)minVal / (double)maxVal;
								}
								else if(DemoPanel.ratioType == 1)
								{
									calcRatio = (double)(maxVal - minVal) / (double)(maxVal + minVal);
								}
								else
								{
									double d = ((double)(maxVal - minVal) / (double)(maxVal + minVal)) + 0.5;
									if(d < 0.0)
										d = 0.0;
									calcRatio = Math.sqrt(d);
								}
								if(calcRatio < 0.0)
									calcRatio = 0.0;
								if(calcRatio > 1.0)
									calcRatio = 1.0;
								finalVal[k] = (byte)(calcRatio * 255.0);
								//fchanout.write(finalVal[k]);
							}
							//System.out.println("Before Write");
							fout.write(finalVal);
							//fchanout.write(finalVal);
						}
					}

					System.out.println("Completed : " + i);
					f1.close();
					fchan.close();
					fout.close();

					int m;
					FileOutputStream o1 = new FileOutputStream(DemoPanel.ImageName[i] + "Output" + i + ".hdr");
					PrintStream ps = new PrintStream(o1);
					//o1.write("BANDS:");
					ps.print("BANDS:      1" + (char)(10));
					ps.print("ROWS:");
					for(m=1; m <= (8-RatioSelection.getDigits(DemoPanel.height)); m++)
						ps.print(" ");
					ps.print(DemoPanel.height);
					ps.print("" + (char)(10));
					ps.print("COLS:");
					for(m=1; m <= (8-RatioSelection.getDigits(DemoPanel.width)); m++)
						ps.print(" ");
					ps.print(DemoPanel.width);
					ps.print("" + (char)(10));
					ps.print("INTERLEAVING:   BIL" + (char)(10));
					ps.print("DATATYPE: U8");
					//ps.print(DemoPanel.shiftFlag);
					ps.print("   " + (char)(10));
					ps.print("BYTE_ORDER: NA      " + (char)(10));
					o1.close();
					ps.close();
				}

			}
			catch(Exception e)
			{
				System.out.println("Saving Output error : " + e);
			}
		}

		if(source == miManual)
		{
			System.out.println("Manual Clicked");
			new CalcSelection();
		}

		if(source == miPCM)
		{
			int i, j, k,ct;
			int X[], V[], A[][];
			int R[], XV1[][], XV2[][], TMP[][];
			double Dij = 0, eta = 0, m=0.0, Uij=0,uth=0;
			ReadImageData ImgData;
			int row, col;
			String input,input2,input3;
			File f;
			FileInputStream f1;
			FileOutputStream fout=null;
			FileChannel fchan;
			MappedByteBuffer mBuf;
			long lstart=0;

			if(DemoPanel.count == 0)
			{
				JOptionPane.showMessageDialog(null, "Please Open The Image First","Message", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			f = new File(DemoPanel.ImageName[0]);
			ImgData = new ReadImageData();
			ImgData.read(f);
			row = ImgData.rows();
			col = ImgData.columns();

			X = new int[DemoPanel.count];
			V = new int[DemoPanel.count];
			A = new int[DemoPanel.count][DemoPanel.count];
			TMP = new int[DemoPanel.count][DemoPanel.count];
			int tmpD[][] = new int[1][1];

			System.out.println("Rows : " + row);
			System.out.println("Cols : " + col);
			for(i=0; i<DemoPanel.count; i++)
			{
				for(j=0; j<DemoPanel.count; j++)
				{
					if(i == j)
						A[i][j] = 1;
					else
						A[i][j] = 0;
				}
			}

			try
			{
				fout = new FileOutputStream(DemoPanel.ImageName[0] + "MuFinal");
			}
			catch(Exception e){}
			if(DemoPanel.calcType == 0)
			{
				for(i=0; i<DemoPanel.count; i++)
				{
					try
					{
						f = new File(DemoPanel.ImageName[i] + "Output" + i);
						f1 = new FileInputStream(f);
						fchan = f1.getChannel();
						lstart = DemoPanel.y * col + DemoPanel.x;
						mBuf = fchan.map(FileChannel.MapMode.READ_ONLY, lstart, 1);
						V[i] = (mBuf.get() << 24) >>> 24;
						f1.close();
						fchan.close();
					}
					catch(Exception e)
					{
						System.out.println("PCM Error: " + e);
					}
				}
			}
			else
			{
				for(i=0; i<DemoPanel.count; i++)
					V[i] = DemoPanel.V[i];
			}

			System.out.print("Vector V:");
			for(i=0; i<DemoPanel.count; i++)
				System.out.print(V[i] + "\t");
			System.out.println("\n\nCalculating ETA From i=0 to " +(row*col) + "...");

			FileInputStream myF[] = new FileInputStream[DemoPanel.count];
			MappedByteBuffer myBuf[] = new MappedByteBuffer[DemoPanel.count];
			FileChannel fc[] = new FileChannel[DemoPanel.count];

			try
			{
				for(j=0; j<DemoPanel.count; j++)
				{
					f = new File(DemoPanel.ImageName[j] + "Output" + j);
					myF[j] = new FileInputStream(f);
					fc[j] = myF[j].getChannel();
					myBuf[j] = fc[j].map(FileChannel.MapMode.READ_ONLY, 0, row*col);
					myF[j].close();
					fc[j].close();
				}
			}
			catch(Exception e)
			{
				System.out.println("Error: " + e);
			}

			for(i=0; i<row*col; i++)
			{
				for(j=0; j<DemoPanel.count; j++)
				{
					try
					{
						/*f = new File(DemoPanel.ImageName[j] + "Output" + j);
						f1 = new FileInputStream(f);
						fchan = f1.getChannel();
						//lstart = DemoPanel.y * col + DemoPanel.x;
						mBuf = fchan.map(FileChannel.MapMode.READ_ONLY, i, 1);*/

						X[j] = (myBuf[j].get() << 24) >>> 24;
						//f1.close();
						//fchan.close();
					}
					catch(Exception e)
					{
						System.out.println("PCM Error: " + e);
					}
				}

				R = RatioSelection.subtract(X, V, DemoPanel.count);
				XV1 = new int[DemoPanel.count][1];
				XV2 = new int[1][DemoPanel.count];
				for(k=0; k<DemoPanel.count; k++)
				{
					XV1[k][0] = R[k];
					XV2[0][k] = R[k];
				}

				TMP = RatioSelection.multiply(XV2, 1, DemoPanel.count, A, DemoPanel.count, DemoPanel.count);
				tmpD = RatioSelection.multiply(TMP, 1, DemoPanel.count, XV1, DemoPanel.count, 1);
				Dij += tmpD[0][0];
				try
				{
					//System.gc();
					if(i%10000 == 0)
						System.out.println("i: " + i);
				}
				catch(Exception e){
					System.out.println(e);
				}
			}

			eta = Dij / (row*col);

			System.out.println("ETA: " + eta);

			System.out.println("Garbage Collection Started");
			try
			{
				System.gc();
			}
			catch(Exception e){}

			System.out.println("Garbage Collection Completed");

			//JOptionPane.showMessageDialog(null, "Value of eta: " + eta,"Message", JOptionPane.INFORMATION_MESSAGE);
			while(m<1.0)
			{
				input = JOptionPane.showInputDialog("Please Enter The Value Of m (1 < m < infinity)");
				m = Double.parseDouble(input);
			}

			System.out.println("Calculating Mu: ");

			try
			{
				for(j=0; j<DemoPanel.count; j++)
				{
					f = new File(DemoPanel.ImageName[j] + "Output" + j);
					myF[j] = new FileInputStream(f);
					fc[j] = myF[j].getChannel();
					myBuf[j] = fc[j].map(FileChannel.MapMode.READ_ONLY, 0, row*col);
					myF[j].close();
					fc[j].close();
				}
			}
			catch(Exception e)
			{
				System.out.println("Error: " + e);
			}
			input2 = JOptionPane.showInputDialog("Please Enter The Value Of thresholding U(0<U<1)");
			uth = Double.parseDouble(input2);
			input3 = JOptionPane.showInputDialog("Please Enter The Value for type of classification you want\n1:Soft Classification\n2:Hard Classification");
			ct = Integer.parseInt(input3);
			for(i=0; i<row*col; i++)
			{
				for(j=0; j<DemoPanel.count; j++)
				{
					try
					{
						/*f = new File(DemoPanel.ImageName[j] + "Output" + j);
						f1 = new FileInputStream(f);
						fchan = f1.getChannel();
						//lstart = DemoPanel.y * col + DemoPanel.x;
						mBuf = fchan.map(FileChannel.MapMode.READ_ONLY, i, 1);*/
						X[j] = (myBuf[j].get() << 24) >>> 24;
						//f1.close();
						//fchan.close();
					}
					catch(Exception e)
					{
						System.out.println("PCM Error: " + e);
					}
				}

				R = RatioSelection.subtract(X, V, DemoPanel.count);
				XV1 = new int[DemoPanel.count][1];
				XV2 = new int[1][DemoPanel.count];
				for(k=0; k<DemoPanel.count; k++)
				{
					XV1[k][0] = R[k];
					XV2[0][k] = R[k];
				}

				TMP = RatioSelection.multiply(XV2, 1, DemoPanel.count, A, DemoPanel.count, DemoPanel.count);
				tmpD = RatioSelection.multiply(TMP, 1, DemoPanel.count, XV1, DemoPanel.count, 1);
				Dij = tmpD[0][0];
				Uij = 1.0 / (1 + Math.pow((Dij/eta), (1/(m-1)) ) );
				byte b = (byte)(Uij * 255.0);
				byte bth = (byte)(uth*255.0);
				try
				{
					if(ct==2){
                               if(b>=bth&&b<0){
                               b=-1;
                               fout.write(b);
                               }
                               else{
                               b=0;
					           fout.write(b);
                               }
					if(i%5000 == 0)
						System.out.println("Byte b: " + b + ", Uij : " + Uij);
				}
				else if(ct==1){
					if(b>=bth&&b<0){
                               fout.write(b);
                               }
                               else{
                               b=0;
					           fout.write(b);
                               }
					if(i%5000 == 0)
						System.out.println("Byte b: " + b + ", Uij : " + Uij);

				}
			}
				catch(Exception e){}
			}
			try
			{
				fout.close();
				System.out.println("Saving Header File Of The MuFinal Output File");
				FileOutputStream o1 = new FileOutputStream(DemoPanel.ImageName[0] + "MuFinal" + ".hdr");
				PrintStream ps = new PrintStream(o1);
				//o1.write("BANDS:");
				ps.print("BANDS:      1" + (char)(10));
				ps.print("ROWS:");
				for(k=1; k <= (8-RatioSelection.getDigits(DemoPanel.height)); k++)
					ps.print(" ");
				ps.print(DemoPanel.height);
				ps.print("" + (char)(10));
				ps.print("COLS:");
				for(k=1; k <= (8-RatioSelection.getDigits(DemoPanel.width)); k++)
					ps.print(" ");
				ps.print(DemoPanel.width);
				ps.print("" + (char)(10));
				ps.print("INTERLEAVING:   BIL" + (char)(10));
				ps.print("DATATYPE: U8");
				//ps.print(DemoPanel.shiftFlag);
				ps.print("   " + (char)(10));
				ps.print("BYTE_ORDER: NA      " + (char)(10));
				o1.close();
				ps.close();
			}
			catch(Exception e)
			{
				System.out.println("After PCM Mu Header: " + e);
			}
			System.out.println("Mu Calculation Completed");
		}

		if(source == miNWE)
		{
			double delta=0.0;
			String input;

			int i, j, k;
			int X[], V[], A[][];
			int R[], XV1[][], XV2[][], TMP[][];
			double Dij = 0, eta = 0, m=0.0, Uij=0, Uij2=0;
			ReadImageData ImgData;
			int row, col;
			File f;
			FileInputStream f1;
			FileOutputStream fout1=null, fout2=null;
			FileChannel fchan;
			MappedByteBuffer mBuf;
			long lstart=0;

			if(DemoPanel.count == 0)
			{
				JOptionPane.showMessageDialog(null, "Please Open The Image First","Message", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			while(m<=0.0)
			{
				input = JOptionPane.showInputDialog("Enter The Value of m > 0");
				m = Double.parseDouble(input);
			}
			while(delta<=0.0)
			{
				input = JOptionPane.showInputDialog("Enter The Value of delta > 0");
				delta = Double.parseDouble(input);
			}

			f = new File(DemoPanel.ImageName[0]);
			ImgData = new ReadImageData();
			ImgData.read(f);
			row = ImgData.rows();
			col = ImgData.columns();

			X = new int[DemoPanel.count];
			V = new int[DemoPanel.count];
			A = new int[DemoPanel.count][DemoPanel.count];
			TMP = new int[DemoPanel.count][DemoPanel.count];
			int tmpD[][] = new int[1][1];

			System.out.println("Rows : " + row);
			System.out.println("Cols : " + col);
			for(i=0; i<DemoPanel.count; i++)
			{
				for(j=0; j<DemoPanel.count; j++)
				{
					if(i == j)
						A[i][j] = 1;
					else
						A[i][j] = 0;
				}
			}

			try
			{
				fout1 = new FileOutputStream(DemoPanel.ImageName[0] + "WE1");
				fout2 = new FileOutputStream(DemoPanel.ImageName[0] + "WE2");
			}
			catch(Exception e){}

			FileInputStream myF[] = new FileInputStream[DemoPanel.count];
			MappedByteBuffer myBuf[] = new MappedByteBuffer[DemoPanel.count];
			FileChannel fc[] = new FileChannel[DemoPanel.count];

			try
			{
				for(j=0; j<DemoPanel.count; j++)
				{
					f = new File(DemoPanel.ImageName[j] + "Output" + j);
					myF[j] = new FileInputStream(f);
					fc[j] = myF[j].getChannel();
					myBuf[j] = fc[j].map(FileChannel.MapMode.READ_ONLY, 0, row*col);
					myF[j].close();
					fc[j].close();
				}
			}
			catch(Exception e)
			{
				System.out.println("Error: " + e);
			}

			if(DemoPanel.calcType == 0)
			{
				for(i=0; i<DemoPanel.count; i++)
				{
					try
					{
						f = new File(DemoPanel.ImageName[i] + "Output" + i);
						f1 = new FileInputStream(f);
						fchan = f1.getChannel();
						lstart = DemoPanel.y * col + DemoPanel.x;
						mBuf = fchan.map(FileChannel.MapMode.READ_ONLY, lstart, 1);
						V[i] = (myBuf[i].get() << 24) >>> 24;
						f1.close();
						fchan.close();
					}
					catch(Exception e)
					{
						System.out.println("PCM Error: " + e);
					}
				}
			}
			else
			{
				for(i=0; i<DemoPanel.count; i++)
					V[i] = DemoPanel.V[i];
			}

			System.out.print("Vector V:");
			for(i=0; i<DemoPanel.count; i++)
				System.out.print(V[i] + "\t");
			System.out.println("\n\nCalculating Ui,j and Ui,c+1...");

			try
			{
				for(j=0; j<DemoPanel.count; j++)
				{
					f = new File(DemoPanel.ImageName[j] + "Output" + j);
					myF[j] = new FileInputStream(f);
					fc[j] = myF[j].getChannel();
					myBuf[j] = fc[j].map(FileChannel.MapMode.READ_ONLY, 0, row*col);
					myF[j].close();
					fc[j].close();
				}
			}
			catch(Exception e)
			{
				System.out.println("Error: " + e);
			}


			for(i=0; i<row*col; i++)
			{
				for(j=0; j<DemoPanel.count; j++)
				{
					try
					{
						//f = new File(DemoPanel.ImageName[j] + "Output" + j);
						//f1 = new FileInputStream(f);
						//fchan = f1.getChannel();
						//lstart = DemoPanel.y * col + DemoPanel.x;
						//mBuf = fchan.map(FileChannel.MapMode.READ_ONLY, i, 1);
						X[j] = (myBuf[j].get() << 24) >>> 24;
						//f1.close();
						//fchan.close();
					}
					catch(Exception e)
					{
						System.out.println("Without Error: " + e);
					}
				}

				R = RatioSelection.subtract(X, V, DemoPanel.count);
				XV1 = new int[DemoPanel.count][1];
				XV2 = new int[1][DemoPanel.count];
				for(k=0; k<DemoPanel.count; k++)
				{
					XV1[k][0] = R[k];
					XV2[0][k] = R[k];
				}

				TMP = RatioSelection.multiply(XV2, 1, DemoPanel.count, A, DemoPanel.count, DemoPanel.count);
				tmpD = RatioSelection.multiply(TMP, 1, DemoPanel.count, XV1, DemoPanel.count, 1);
				Dij = tmpD[0][0];
				Uij = 1.0 / (1 + Math.pow((Dij/delta), (1/(m-1)) ) );
				Uij2 = 1.0 / (1 + Math.pow((delta/Dij), (1/(m-1)) ) );
				byte b = (byte)(Uij * 255.0);
				byte b2 = (byte)(Uij2 * 255.0);
				try
				{
					fout1.write(b);
					fout2.write(b2);
					if(i%5000 == 0)
						System.out.println("Byte b: " + b + ", Uij : " + Uij);
				}
				catch(Exception e){}
			}
			try
			{
				fout1.close();
				fout2.close();
				FileOutputStream o1 = new FileOutputStream(DemoPanel.ImageName[0] + "WE1" + ".hdr");
				FileOutputStream o2 = new FileOutputStream(DemoPanel.ImageName[0] + "WE2" + ".hdr");
				PrintStream ps = new PrintStream(o1);
				PrintStream ps2 = new PrintStream(o2);
				//o1.write("BANDS:");
				ps.print("BANDS:      1" + (char)(10));
				ps.print("ROWS:");
				for(k=1; k <= (8-RatioSelection.getDigits(DemoPanel.height)); k++)
					ps.print(" ");
				ps.print(DemoPanel.height);
				ps.print("" + (char)(10));
				ps.print("COLS:");
				for(k=1; k <= (8-RatioSelection.getDigits(DemoPanel.width)); k++)
					ps.print(" ");
				ps.print(DemoPanel.width);
				ps.print("" + (char)(10));
				ps.print("INTERLEAVING:   BIL" + (char)(10));
				ps.print("DATATYPE: U8");
				//ps.print(DemoPanel.shiftFlag);
				ps.print("   " + (char)(10));
				ps.print("BYTE_ORDER: NA      " + (char)(10));
				o1.close();
				ps.close();

				ps2.print("BANDS:      1" + (char)(10));
				ps2.print("ROWS:");
				for(k=1; k <= (8-RatioSelection.getDigits(DemoPanel.height)); k++)
					ps2.print(" ");
				ps2.print(DemoPanel.height);
				ps2.print("" + (char)(10));
				ps2.print("COLS:");
				for(k=1; k <= (8-RatioSelection.getDigits(DemoPanel.width)); k++)
					ps2.print(" ");
				ps2.print(DemoPanel.width);
				ps2.print("" + (char)(10));
				ps2.print("INTERLEAVING:   BIL" + (char)(10));
				ps2.print("DATATYPE: U8");
				//ps.print(DemoPanel.shiftFlag);
				ps2.print("   " + (char)(10));
				ps2.print("BYTE_ORDER: NA      " + (char)(10));
				o2.close();
				ps2.close();
			}
			catch(Exception e)
			{
				System.out.println("After Noise Clustering Without Entropy: " + e);
			}
			System.out.println("Calculation Completed");

			/////////////////////////////////

			//JOptionPane.showMessageDialog(null, "Noise Without Entropy Clicked","Message", JOptionPane.INFORMATION_MESSAGE);
		}

		if(source == miNE)
		{
			//JOptionPane.showMessageDialog(null, "Noise With Entropy Clicked","Message", JOptionPane.INFORMATION_MESSAGE);
						double delta=0.0;
			String input;

			int i, j, k;
			int X[], V[], A[][];
			int R[], XV1[][], XV2[][], TMP[][];
			double Dij = 0, eta = 0, m=0.0, Uij=0, Uij2=0;
			ReadImageData ImgData;
			int row, col;
			File f;
			FileInputStream f1;
			FileOutputStream fout1=null, fout2=null;
			FileChannel fchan;
			MappedByteBuffer mBuf;
			long lstart=0;

			if(DemoPanel.count == 0)
			{
				JOptionPane.showMessageDialog(null, "Please Open The Image First","Message", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			while(m<=0.0)
			{
				input = JOptionPane.showInputDialog("Enter The Value of v (nu) > 0");
				m = Double.parseDouble(input);
			}
			while(delta<=0.0)
			{
				input = JOptionPane.showInputDialog("Enter The Value of delta > 0");
				delta = Double.parseDouble(input);
			}

			f = new File(DemoPanel.ImageName[0]);
			ImgData = new ReadImageData();
			ImgData.read(f);
			row = ImgData.rows();
			col = ImgData.columns();

			X = new int[DemoPanel.count];
			V = new int[DemoPanel.count];
			A = new int[DemoPanel.count][DemoPanel.count];
			TMP = new int[DemoPanel.count][DemoPanel.count];
			int tmpD[][] = new int[1][1];

			System.out.println("Rows : " + row);
			System.out.println("Cols : " + col);
			for(i=0; i<DemoPanel.count; i++)
			{
				for(j=0; j<DemoPanel.count; j++)
				{
					if(i == j)
						A[i][j] = 1;
					else
						A[i][j] = 0;
				}
			}

			try
			{
				fout1 = new FileOutputStream(DemoPanel.ImageName[0] + "E1");
				fout2 = new FileOutputStream(DemoPanel.ImageName[0] + "E2");
			}
			catch(Exception e){}
			if(DemoPanel.calcType == 0)
			{
				for(i=0; i<DemoPanel.count; i++)
				{
					try
					{
						f = new File(DemoPanel.ImageName[i] + "Output" + i);
						f1 = new FileInputStream(f);
						fchan = f1.getChannel();
						lstart = DemoPanel.y * col + DemoPanel.x;
						mBuf = fchan.map(FileChannel.MapMode.READ_ONLY, lstart, 1);
						V[i] = (mBuf.get() << 24) >>> 24;
						f1.close();
						fchan.close();
					}
					catch(Exception e)
					{
						System.out.println("PCM Error: " + e);
					}
				}
			}
			else
			{
				for(i=0; i<DemoPanel.count; i++)
					V[i] = DemoPanel.V[i];
			}

			System.out.print("Vector V:");
			for(i=0; i<DemoPanel.count; i++)
				System.out.print(V[i] + "\t");
			System.out.println("\n\nCalculating Ui,j and Ui,c+1...");

			FileInputStream myF[] = new FileInputStream[DemoPanel.count];
			MappedByteBuffer myBuf[] = new MappedByteBuffer[DemoPanel.count];
			FileChannel fc[] = new FileChannel[DemoPanel.count];

			try
			{
				for(j=0; j<DemoPanel.count; j++)
				{
					f = new File(DemoPanel.ImageName[j] + "Output" + j);
					myF[j] = new FileInputStream(f);
					fc[j] = myF[j].getChannel();
					myBuf[j] = fc[j].map(FileChannel.MapMode.READ_ONLY, 0, row*col);
					myF[j].close();
					fc[j].close();
				}
			}
			catch(Exception e)
			{
				System.out.println("Error: " + e);
			}

			for(i=0; i<row*col; i++)
			{
				for(j=0; j<DemoPanel.count; j++)
				{
					try
					{
						//f = new File(DemoPanel.ImageName[j] + "Output" + j);
						//f1 = new FileInputStream(f);
						//fchan = f1.getChannel();
						//lstart = DemoPanel.y * col + DemoPanel.x;
						//mBuf = fchan.map(FileChannel.MapMode.READ_ONLY, i, 1);
						X[j] = (myBuf[j].get() << 24) >>> 24;
						//f1.close();
						//fchan.close();
					}
					catch(Exception e)
					{
						System.out.println("PCM Error: " + e);
					}
				}

				R = RatioSelection.subtract(X, V, DemoPanel.count);
				XV1 = new int[DemoPanel.count][1];
				XV2 = new int[1][DemoPanel.count];
				for(k=0; k<DemoPanel.count; k++)
				{
					XV1[k][0] = R[k];
					XV2[0][k] = R[k];
				}

				TMP = RatioSelection.multiply(XV2, 1, DemoPanel.count, A, DemoPanel.count, DemoPanel.count);
				tmpD = RatioSelection.multiply(TMP, 1, DemoPanel.count, XV1, DemoPanel.count, 1);
				Dij = tmpD[0][0];
				Uij = Math.exp(Dij / m * -1.0) / (Math.exp(Dij / m * -1.0) + Math.exp(delta / m * -1.0));
				Uij2 = Math.exp(delta / m * -1.0) / (Math.exp(Dij / m * -1.0) + Math.exp(delta / m * -1.0));
				byte b = (byte)(Uij * 255.0);
				byte b2 = (byte)(Uij2 * 255.0);
				try
				{
					fout1.write(b);
					fout2.write(b2);
					if(i%5000 == 0)
						System.out.println("Byte b: " + b + ", Uij : " + Uij);
				}
				catch(Exception e){}
			}
			try
			{
				fout1.close();
				fout2.close();
				FileOutputStream o1 = new FileOutputStream(DemoPanel.ImageName[0] + "E1" + ".hdr");
				FileOutputStream o2 = new FileOutputStream(DemoPanel.ImageName[0] + "E2" + ".hdr");
				PrintStream ps = new PrintStream(o1);
				PrintStream ps2 = new PrintStream(o2);
				//o1.write("BANDS:");
				ps.print("BANDS:      1" + (char)(10));
				ps.print("ROWS:");
				for(k=1; k <= (8-RatioSelection.getDigits(DemoPanel.height)); k++)
					ps.print(" ");
				ps.print(DemoPanel.height);
				ps.print("" + (char)(10));
				ps.print("COLS:");
				for(k=1; k <= (8-RatioSelection.getDigits(DemoPanel.width)); k++)
					ps.print(" ");
				ps.print(DemoPanel.width);
				ps.print("" + (char)(10));
				ps.print("INTERLEAVING:   BIL" + (char)(10));
				ps.print("DATATYPE: U8");
				//ps.print(DemoPanel.shiftFlag);
				ps.print("   " + (char)(10));
				ps.print("BYTE_ORDER: NA      " + (char)(10));
				o1.close();
				ps.close();

				ps2.print("BANDS:      1" + (char)(10));
				ps2.print("ROWS:");
				for(k=1; k <= (8-RatioSelection.getDigits(DemoPanel.height)); k++)
					ps2.print(" ");
				ps2.print(DemoPanel.height);
				ps2.print("" + (char)(10));
				ps2.print("COLS:");
				for(k=1; k <= (8-RatioSelection.getDigits(DemoPanel.width)); k++)
					ps2.print(" ");
				ps2.print(DemoPanel.width);
				ps2.print("" + (char)(10));
				ps2.print("INTERLEAVING:   BIL" + (char)(10));
				ps2.print("DATATYPE: U8");
				//ps.print(DemoPanel.shiftFlag);
				ps2.print("   " + (char)(10));
				ps2.print("BYTE_ORDER: NA      " + (char)(10));
				o2.close();
				ps2.close();
			}
			catch(Exception e)
			{
				System.out.println("After Noise Clustering With Entropy: " + e);
			}
			System.out.println("Calculation Completed");

		}

		if (source == miSave)
		{
			System.out.println("Clicked");

		}

		if (source == miExit)
		{
			System.exit(0);
		}

		if (source == miAbout)
		{
			l1 = new JLabel();
			l1.setBounds(100, 100, 375, 100);
			l1.setFont(f);
			l1.setForeground(Color.RED);
			con.add(l1);
		}

		if (source == miContrast)
		{
	    	Enhancement E = new Enhancement();
        	E.enhances(p,q,r);
        	img=E.getpicdata();
			DemoPanel.EFactor = 1;
			pane=new DemoPanel(img);
			pane.repaint();
            ContentPane.removeAll();
		    ContentPane.add(new JScrollPane(pane));
			validate();
			System.out.println(source);

			System.out.println("Clicked");
		}

		if (source == mifcc)
		{
			if (dialog == null)
				dialog = new AboutDialog(this);
			dialog.setSize(500, 150);
			dialog.setVisible(true);
			DemoPanel.EFactor = 1;
			p = dialog.show_band1();
			q = dialog.show_band2();
			r = dialog.show_band3();
			picture Picture1 = new picture();
			img = Picture1.getpicdata(p, q, r);
			pane = new DemoPanel(img);
			pane.repaint();
			ContentPane.removeAll();
			ContentPane.add(new JScrollPane(pane));
			validate();


		}

		if(source==zin)
		{
	         System.out.println("zoom in");
	         String in=JOptionPane.showInputDialog(null,"Enter the Enlargement factor for zoom ");
	         int f= Integer.parseInt(in);
	         ZoomIn zoompane = new ZoomIn(img,f);
             img=zoompane.retchangepic();
             DemoPanel.EFactor = (double)(DemoPanel.EFactor * (double)f);
			 pane=new DemoPanel(img);
			 pane.repaint();
             ContentPane.removeAll();
		     ContentPane.add(new JScrollPane(pane));
			 validate();
			  System.out.println("Factor : " + DemoPanel.EFactor);
	    }

	    if(source==zout)
	    {
	    	 System.out.println("zoom out");
	    	 String in=JOptionPane.showInputDialog(null,"Enter the shrinking factor for zoom out");
	         int f= Integer.parseInt(in);
	         ZoomOut zoompane=new ZoomOut(img,f);
	         DemoPanel.EFactor = (double)(DemoPanel.EFactor / (double)f);
             img=zoompane.retchangepic();
			 pane=new DemoPanel(img);
			 pane.repaint();
             ContentPane.removeAll();
		     ContentPane.add(new JScrollPane(pane));
			 validate();
			System.out.println("Factor : " + DemoPanel.EFactor);

	    }

		if (source == miCross)
		{
			cross = new JFrame("Cross Checking");
			cross.setSize(300, 200);
			cross.setVisible(true);

			cross.addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent we)
				{
					cross.setVisible(false);
				}
			});
			cross.setLayout(new FlowLayout());
			JLabel jlBand = new JLabel("Enter Band Value  ");
			cross.add(jlBand);
			jtBand = new JTextField(10);
			cross.add(jtBand);
			JLabel jlRow = new JLabel("Enter Row Value  ");
			cross.add(jlRow);
			jtRow = new JTextField(10);
			cross.add(jtRow);
			JLabel jlCol = new JLabel("Enter Column Value");
			cross.add(jlCol);
			jtCol = new JTextField(10);
			cross.add(jtCol);

			okBut = new JButton("Submit");
			cross.add(okBut);
			okBut.addActionListener(this);


		}

		if (source == okBut)
		{
			int myB, myR, myC, val;
			myB = Integer.parseInt (jtBand.getText());
			myR = Integer.parseInt(jtRow.getText());
			myC = Integer.parseInt(jtCol.getText());
			if (myB >= ReadImageData.Bands)
			{
				JOptionPane.showMessageDialog(null, "Invalid Band Number" , "Message", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			val = picture.a[myB][myR][myC];
			JLabel ans = new JLabel("Value : " + val);
			cross.add(ans);
			JOptionPane.showMessageDialog(null, "Value is : " + val,"Message", JOptionPane.INFORMATION_MESSAGE);
		}


	}

}

class ReadImageData
{
	private static File picfile;
	public static int Bands;
	private static int Rows;
	private static int Columns;

	public ReadImageData()
	{

	}

	public ReadImageData(File file)
	{
		String name = file.getName();
		this.picfile = file;
	}

	public void read(File Name)//read image file name
	{
		int index = 0;
		int b1 = 0;
		int row = 0;
		int col = 0;
		int x = 0;
		int c[];
		InputStream f1;
		char p[] = new char[45];
		String ban = new String();
		String row1 = new String();
		String col1 = new String();
		this.picfile = Name;
		String s[] = new String[30];
		try
		{
			f1 = new FileInputStream(Name + ".hdr");

			c = new int[11];
			for (int i = 0; i <= 43; i = i + 1)
			{
				p[i] = (char)f1.read();
			}
			f1.close();
		}
		catch (Exception e)
		{
		}

		for (int i = 6; i < 13; i = i + 1)
		{
			if (p[i] == '\n') break;
			if (p[i] == ' ') p[i] = '0';
			ban = ban + p[i];
		}
		b1 = Integer.parseInt(ban);
		this.Bands = b1;
		System.out.println("ReadImage Band : " + b1);
		for (int i = 20; i <= 27; i = i + 1)
		{
			if (p[i] == '\n') break;
			if (p[i] == ' ') p[i] = '0';
			row1 = row1 + p[i];
		}
		row = Integer.parseInt(row1);
		System.out.println("ReadImage Row : " + row);
		this.Rows = row;
		for (int i = 35; i <= 42; i = i + 1)
		{
			if (p[i] == '\n') break;
			if (p[i] == ' ') p[i] = '0';
			col1 = col1 + p[i];
		}
		System.out.println("This is col1: " + col1);
		col = Integer.parseInt(col1);
		this.Columns = col;
		DemoPanel.bands = Bands;
		System.gc();
	}

	public int bands()//return bands
	{
		return Bands;
	}
	public int rows()//return rows
	{
		return Rows;
	}
	public int columns()//return columns
	{
		return Columns;
	}

	public File imagefile()//return imagefilename
	{
		return picfile;
	}
}

class picture extends JPanel
{
	private BufferedImage img;
	private int Width;
	private int Height;
	private int Bands;
	private ReadImageData ImgData1;
	static private File ImageFile;
	static int[][][] a;
	int array_size;
	static String ImageName;
	static int maxBand[], minBand[];
	int x_pass,y_pass,y_pass2,num_of_slice,origin_x=0,origin_y=0;

	public static void setimage(String nam)
	{
		ImageFile = new File(nam);
		ImageName = nam;
	}


	public BufferedImage getpicdata(int Color1, int Color2, int Color3)///return image
	{
		System.gc();
		System.out.println("My Getpic method");
		ImgData1 = new ReadImageData();
		ImageFile = ImgData1.imagefile();
		System.out.println(ImageFile);

		ImgData1.read(ImageFile);
		Bands = ImgData1.bands();
		Width = ImgData1.columns();
		Height = ImgData1.rows();
		int p = Color1;
		int q = Color2;
		int r = Color3;
		System.out.println("Bands : " + Bands);
		System.out.println("Width : " + Width);
		System.out.println("Height : " + Height);
		DemoPanel.bands = Bands;
		System.gc();
		int i=0, x, j=0, l, k;
		int index = 0;

		int pix[];
		int pix1[];
		int pix2[];
		int pix3[];

		int pix_2[];
		int pix1_2[];
		int pix2_2[];
		int pix3_2[];

		int p1, p2;
		int z,g;
		char temp[] = new char[10];
		char temp2[] = new char[100];
		int myFlag = 0;
		int shiftFlag=0;
		int maxRGB = 0, tempMax = 0;
		byte temp_max[];


		try
		{
			InputStream MyHdr = new FileInputStream(ImageFile + ".hdr");
			for (k = 0; k < 73; k++)
				temp2[k] = (char)MyHdr.read();
			for (k = 73; k < 76; k++)
			{
				temp[k - 73] = (char)MyHdr.read();
			}
		}
		catch (Exception e) { myFlag = 1; }
		String myTemp = (new String(temp)).trim();
		System.out.println("Read Data : " + myTemp);
		try
		{
			String s = myTemp.substring(0);
			shiftFlag = Integer.parseInt (s);
		}
		catch(Exception e){ myFlag = 1;}

		System.out.println("My Shift : " + shiftFlag);
		if(myFlag == 1)
		{
			shiftFlag = 8;
			System.out.println("My Imageddddddddddddddd");
		}
		long fsize, fsize2;

		MappedByteBuffer mBuf;
		int tempWidth;

		try
		{

			FileInputStream f1 = new FileInputStream(ImageFile);            //reading pixel values for each band
			FileChannel fchan = f1.getChannel();
			FileOutputStream fout[] = new FileOutputStream[Bands];
			FileChannel fchanOut[] = new FileChannel[Bands];

			fsize = fchan.size();

			myFlag = 0;
			try
			{
				FileInputStream chk = new FileInputStream("Data" + ImageName +  "0");
			}
			catch(FileNotFoundException e)
			{
				myFlag = 1;
			}
			if(myFlag == 1)
			{

				for(k=0; k<Bands; k++)
				{

					fout[k] = new FileOutputStream("Data" + ImageName +  k);

					fchanOut[k] = fout[k].getChannel();

				}

				int pos=0;
				index=1;
				System.out.println("My Database Creation Started");



				tempWidth = Width;


				if (shiftFlag != 8)
					tempWidth = Width * 2;


				index=1;
				for(i=0;i<Height*Bands;i++)
				{
					mBuf=fchan.map(FileChannel.MapMode.READ_ONLY,pos,tempWidth);
					pos=pos+tempWidth;
					fchanOut[index-1].write(mBuf);

					index=index+1;
					if(index==(Bands+1))
						index=1;
				}

				f1.close();
				fchan.close();
				for(i=0;i<Bands;i++)
				{
					fout[i].close();
					fchanOut[i].close();
				}

			}
			System.gc();
		}
		catch (Exception e) { System.out.println("Before Database creation : " + e); }

		System.out.println("My Database Created");

		img = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_ARGB);
		WritableRaster raster = img.getRaster();

		FileInputStream fin_band[]=new FileInputStream[Bands];
 		FileChannel fchan_in[]=new FileChannel[Bands];

 		index = 0;
 		double myTempCalc;

 		int slices, y_pass, y_origin;
 		int x_win=Width,y_win=Bands*100,y_win2,flag1=0;
 		int uneven_flag = 1;

 		if((Height % y_win) == 0)
 		{
 			slices = (int)(Height/y_win);
 			uneven_flag = 0;
 		}
 		else
 			slices = (int)(Height/y_win)+1;

 		fsize2=((Height-(slices-1)*y_win)*x_win);
 		y_win2 = (Height-(slices-1)*y_win);
		if(Height>y_win || Width>x_win)
		{
		 	fsize=(y_win*x_win);
		 	flag1=1;
		 	y_pass=y_win;

		}
	 	else
		{
		 	fsize=(Height*Width);
		 	System.out.println("Fsize : " + fsize);
		 	flag1=0;
		 	y_pass=Width;
		}


		int band_num[] = new int[Bands + 4];
		String input;
		if(Bands != 1)
		{
		/*	int user;
		 	input = JOptionPane.showInputDialog("Please specify the band for Red Gun\n"+"Following options are from '1' -'"+Bands+"'\n");
		 	user=Integer.parseInt(input);
		 	while((user>Bands) || (user<1))
			{
				JOptionPane.showMessageDialog(null,"Invalid Band Number","Message",JOptionPane.WARNING_MESSAGE);
				input = JOptionPane.showInputDialog("Please specify the band for Red Gun\n"+"Following options are from '1' -'"+Bands+"'\n");
				user = Integer.parseInt(input);
			}

		 	//convert string to integer value
	   	 	BilImage.p = p = band_num[0] = Integer.parseInt(input);

		 	input = JOptionPane.showInputDialog("Please specify the band for Green Gun\n"+"Following options are from '1' -'"+Bands+"'\n");
		 	user = Integer.parseInt(input);
		 	while((user>Bands) || (user<1))
			{
				JOptionPane.showMessageDialog(null,"Invalid Band Number","Message",JOptionPane.WARNING_MESSAGE);
				input = JOptionPane.showInputDialog("Please specify the band for Green Gun\n"+"Following options are from '1' -'"+Bands+"'\n");
				user = Integer.parseInt(input);
			}
		 	//convert string to integer value
 	   	 	BilImage.q = q = band_num[1] = Integer.parseInt(input);

		 	input = JOptionPane.showInputDialog("Please specify the band for Blue Gun\n"+"Following options are from '1' -'"+Bands+"'\n");
 		 	user = Integer.parseInt(input);
 		 	while((user>Bands) || (user<1))
			{
				JOptionPane.showMessageDialog(null,"Invalid Band Number","Message",JOptionPane.WARNING_MESSAGE);
				input = JOptionPane.showInputDialog("Please specify the band for Blue Gun\n"+"Following options are from '1' -'"+Bands+"'\n");
				//convert string to integer value
				user = Integer.parseInt(input);
			}
 		 	//convert string to integer value
 	   	 	BilImage.r = r = band_num[2] = Integer.parseInt(input);
			//name_of_image="band";*/
		}
		else
		{
			//JOptionPane.showMessageDialog(null,"Enter the name of the single band image","Message",JOptionPane.WARNING_MESSAGE);
			p = q = r = 1;
		}

		byte pix_byte[],pix_rgb[][],pix_rgb2[][];



 		try
 		{
 			for(k=0; k<Bands; k++)
 			{
 				fin_band[k] = new FileInputStream("Data" + ImageName +  k);
 				fchan_in[k] = fin_band[k].getChannel();
 			}

			if(shiftFlag == 8)
 			{
	 			pix = new int [(int)fsize];
				pix1 = new int [(int)fsize];
				pix2 = new int [(int)fsize];
				pix3 = new int [(int)fsize];

				pix_2 = new int [(int)fsize2];
			 	pix1_2 = new int [(int)fsize2];
				pix2_2 = new int [(int)fsize2];
				pix3_2 = new int [(int)fsize2];

	 			pix_rgb=new byte[Bands][(int)fsize];
				pix_rgb2=new byte[Bands][(int)fsize2];
			}
			else
			{
				pix = new int [(int)fsize];
				pix1 = new int [(int)fsize];
				pix2 = new int [(int)fsize];
				pix3 = new int [(int)fsize];

				pix_2 = new int [(int)fsize2];
			 	pix1_2 = new int [(int)fsize2];
				pix2_2 = new int [(int)fsize2];
				pix3_2 = new int [(int)fsize2];

	 			pix_rgb=new byte[Bands][(int)fsize*2];
				pix_rgb2=new byte[Bands][(int)fsize2*2];

			}


			byte pix_line[];
			byte pix_line2[];
			int abc=0;
			if(shiftFlag == 8)
			{
				pix_line = new byte[x_win];
				pix_line2 = new byte[x_win];
				abc = x_win;
			}
			else
			{
				pix_line = new byte[x_win * 2];
				pix_line2 = new byte[x_win * 2];
				abc = x_win * 2;
			}
			int line_start,line_start2=0;
			int myMax = 0;
			int myMin=0;
			long count;

			int tempCount;
			if(shiftFlag == 8)
				count = fsize;
			else
				count = fsize * 2;


			maxBand=new int[Bands];
			minBand=new int[Bands];
			long counter=0;
			long start_pos = 0;

			int maxBand2[]=new int[Bands];
		   	int minBand2[]=new int[Bands];
		   	int myMin2=0;
		   	int myMax2=0;
		   	line_start = 0;
		   	int first_time=1;

		   	for(i=0; i<Bands; i++)
		   	{
		   		maxBand[i] = -1;
		   		minBand[i] = 65535;
		   	}

		   	//Calculation of Maximum and Minimum


		   	FileInputStream f1 = new FileInputStream(ImageFile);
			FileChannel fchan = f1.getChannel();

		   	if(shiftFlag == 8)
				tempWidth = Width;
			else
				tempWidth = Width * 2;
			System.out.println("Calculating Maximum and Minimum");
		   	for(j=0; j<Height; j++)
		   	{

		   		for(i=0; i<Bands; i++)
		   		{
		   			mBuf = fchan.map(FileChannel.MapMode.READ_ONLY,line_start,tempWidth);
					mBuf.get(pix_line);
					for(k=0; k<tempWidth; k++)
						pix_rgb[i][k] = pix_line[k];
					line_start += tempWidth;

					for(k=0; k<Width; k++)
					{
						if(shiftFlag != 8)
						{
							p1 = pix_rgb[i][2*k] << 24;
							p2 = (pix_rgb[i][2*k+1] <<24 ) >>> 8;
							tempCount = p1 | p2;
							tempCount = tempCount >>> 16;
						}
						else
							tempCount = (pix_rgb[i][k] << 24) >>> 24;

						if(tempCount>maxBand[i])
							maxBand[i]=tempCount;

						if(tempCount<minBand[i])
						   minBand[i]=tempCount;
					}
		   		}
		   		if(j%(Bands*100) == 0)
		   		{
		   			System.out.println("j : " + j);
		   			System.gc();
		   		}
		   		//System.out.println("j : " + j);
		   	}

			myMax = maxBand[0];
			for(i=1; i<Bands; i++)
				if(myMax < maxBand[i])
					myMax = maxBand[i];

			for(i=0;i<Bands;i++)
				 System.out.println("the maximum value for band"+ (i+1 )+ " " + maxBand[i]);

			for(i=0;i<Bands;i++)
				System.out.println("the minimum value for band"+ (i+1) + " " + minBand[i]);


			// when size of image is smaller than window size set by x_win & y_win
			if(flag1==0)
			{

				for(i=0; i<Bands; i++)
				{
					if(shiftFlag == 8)
						mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,0,fsize);
					else
						mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,0,fsize*2);
					mBuf.get(pix_rgb[i]);
				}
				System.gc();
                System.out.println("HIIIIIIIIIIIIIIIII Max Value : " + myMax);
 				//convert raw bytes into arrays.
 				if(shiftFlag == 8)
 				{
 					System.out.println("This is an 8 bit image");
					for(i=0;i<fsize;i++)
					{
						pix[i]=255;
						if(Bands!=1)
						{
							if(pix_rgb[p-1][i]>=0)
								pix1[i]= pix_rgb[p-1][i];
							else
							  	pix1[i]= 256+pix_rgb[p-1][i];

							if(pix_rgb[q-1][i]>=0)
							  	pix2[i]= pix_rgb[q-1][i];
							else
							  	pix2[i]= 256+pix_rgb[q-1][i];

							if(pix_rgb[r-1][i]>=0)
							  	pix3[i]= pix_rgb[r-1][i];
							else
							  	pix3[i]= 256+pix_rgb[r-1][i];

						}

						else
						{
							if(pix_rgb[0][i]>=0)
							{
								pix1[i]= pix_rgb[0][i];
								pix2[i]=pix1[i];
								pix3[i]=pix1[i];
							}
							else
							{
								pix1[i]= 256+pix_rgb[0][i];
								pix2[i]=pix1[i];
								pix3[i]=pix1[i];
							}
						 }
	    	      	}//close for
				}
				else
				{

					for(i=0;i<fsize;i++)
					{
						pix[i]=255;
						if(Bands!=1)
						{
							//for pix1
							if(pix_rgb[p-1][2*i]<0)
								z=256+pix_rgb[p-1][2*i];
							else
								z=pix_rgb[p-1][2*i];

							if(pix_rgb[p-1][2*i+1]<0)
								g=256+pix_rgb[p-1][2*i+1];
							else
								g=pix_rgb[p-1][2*i+1];

							p1 = z << 8;
							p2 = g;
							pix1[i] = p1 | p2;

							myTempCalc = ((double)pix1[i] / (double)myMax) * 255.0;
							pix1[i] = (int)myTempCalc;

							// for pix2
							if(pix_rgb[q-1][2*i]<0)
								z=256+pix_rgb[q-1][2*i];
							else
								z=pix_rgb[q-1][2*i];

							if(pix_rgb[q-1][2*i+1]<0)
								g=256+pix_rgb[q-1][2*i+1];
							else
								g=pix_rgb[q-1][2*i+1];

							p1 = z << 8;
							p2 = g;
							pix2[i] = p1 | p2;
							if (i==257601)
							System.out.println("Hello");

							myTempCalc = ((double)pix2[i] / (double)myMax) * 255.0;
							pix2[i] = (int)myTempCalc;

							// for pix3

							if(pix_rgb[r-1][2*i]<0)
								z=256+pix_rgb[r-1][2*i];
							else
								z=pix_rgb[r-1][2*i];

							if(pix_rgb[r-1][2*i+1]<0)
								g=256+pix_rgb[r-1][2*i+1];
							else
								g=pix_rgb[r-1][2*i+1];

							p1 = z << 8;
							p2 = g;
							pix3[i] = p1 | p2;

							myTempCalc = ((double)pix3[i] / (double)myMax) * 255.0;
							pix3[i] = (int)myTempCalc;

						}

						else
						{


							/*if(pix_rgb[0][i]>=0)
							{
								pix1[i]= pix_rgb[0][i];
								pix2[i]=pix1[i];
								pix3[i]=pix1[i];
							}
							else
							{
								pix1[i]= 256+pix_rgb[0][i];
								pix2[i]=pix1[i];
								pix3[i]=pix1[i];
							}*/
						 }

	    	      	}//close for
					System.out.println("Image Complete");
				}


				raster.setSamples(0,0,Width,Height,0,pix1);
				raster.setSamples(0,0,Width,Height,1,pix2);
				raster.setSamples(0,0,Width,Height,2,pix3);
				raster.setSamples(0,0,Width,Height,3,pix);
				System.out.println("Image Code");
				System.gc();


			}//close if
			else if(flag1==1) // when size of image is greater than window size set by x_win & y_win
			{

				System.out.println("Maximum Value : " + myMax);

				if(shiftFlag==8)
				{
					line_start=0;
					for(int v=0;v<slices;v++)
					{
						System.out.println("enter v :"+v);

						if(v!=(slices-1))
						{

				  		  for( i=0;i<Bands;i++)
						  {
							if(Bands==1 && i!=0)
							{
								continue;
							}

							//line_start=line_start2;
							line_start=v*y_win*Width;
							System.out.println("Line Start : " + line_start + " i : " + i);
							int k_index=0;
							// read the files line by line i.e x_win bytes in one go
							// and repeat unpo y_win i.e. size of window
							for(j=0;j<y_win;j++)
							{
								mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,line_start,x_win);
								mBuf.get(pix_line);// store each line in separate buffer
								// i.e pix_line[]
								line_start=line_start+Width;

								// increment line_start by Width to point to next line
								// and leave rest of the pixels
								for(k=0;k<x_win;k++,k_index++)
								{
								// transfer one line data into pix_rgb[][]
								pix_rgb[i][k_index]=pix_line[k];

								}
							}//close for

							//System.out.println("i :"+i);


				    	}//close for

						//line_start2=line_start;
							System.out.println("enter ");
							System.out.println("fsize :"+fsize);

						if(Bands!=1)
						{
							for( i=0;i<fsize;i++)
							{
				    		    pix[i]=255;
				    		    if(pix_rgb[p-1][i]>=0)
							    pix1[i]= pix_rgb[p-1][i];
								else
								pix1[i]= 256+pix_rgb[p-1][i];

								if(pix_rgb[q-1][i]>=0)
								pix2[i]= pix_rgb[q-1][i];
								else
								pix2[i]= 256+pix_rgb[q-1][i];

								if(pix_rgb[r-1][i]>=0)
								pix3[i]= pix_rgb[r-1][i];
								else
								pix3[i]= 256+pix_rgb[r-1][i];

							}

							raster.setSamples(0,origin_y,x_win,y_win,0,pix1);
							raster.setSamples(0,origin_y,x_win,y_win,1,pix2);
							raster.setSamples(0,origin_y,x_win,y_win,2,pix3);
				 			raster.setSamples(0,origin_y,x_win,y_win,3,pix);
				 			System.out.println("slice displayed");
							origin_x=0;
							origin_y=origin_y+y_win;
							System.gc();

					    }
						else
						{
							  for( i=0;i<fsize;i++)
							  {
							      pix[i]=255;
							      if(pix_rgb[0][i]>=0)
							      pix1[i]= pix_rgb[0][i];
							      else
							  	  pix1[i]= 256+pix_rgb[0][i];

							  }
							  pix2=pix3=pix1;

							raster.setSamples(0,origin_y,x_win,y_win,0,pix1);
							raster.setSamples(0,origin_y,x_win,y_win,1,pix1);
							raster.setSamples(0,origin_y,x_win,y_win,2,pix1);
						 	raster.setSamples(0,origin_y,x_win,y_win,3,pix);

							System.out.println("Else Slice displayed");
						 	origin_x=0;
						 	origin_y=origin_y+y_win;

						}
						System.gc();

					}//close if(v!=num_of_slice-1)

					else if(v==(slices-1))
					{
						System.out.println("v origin_y line_start  :"+v+" "+origin_y+" "+line_start);
						for( i=0;i<Bands;i++)
						{
							if(Bands==1 && i!=0)
							{
								continue;
							}
							line_start=v*y_win*Width;
							System.out.println("line start2 i :"+line_start+" "+i);
							int k_index=0;
							// read the files line by line i.e x_win bytes in one go
							// and repeat unpo y_win i.e. size of window

						    for(j=0;j<(Height-(slices-1)*y_win);j++)
							{
								mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,line_start,x_win);
								mBuf.get(pix_line);// store each line in separate buffer
								// i.e pix_line[]
								line_start=line_start+Width;
								// increment line_start by Width to point to next line
								// and leave rest of the pixels
								for(k=0;k<x_win;k++,k_index++)
								{
									// transfer one line data into pix_rgb[][]
									pix_rgb2[i][k_index]=pix_line[k];

								}

							}//close for
							System.out.println("i :"+i);
						}//close for

						System.out.println("enter ");
						System.out.println("fsize2 :"+fsize2);

						if(Bands!=1)
						{
							for( i=0;i<fsize2;i++)
							{
								pix_2[i]=255;

							    if(pix_rgb2[p-1][i]>=0)
							    pix1_2[i]= pix_rgb2[p-1][i];
							    else
							    pix1_2[i]= 256+pix_rgb2[p-1][i];

							    if(pix_rgb2[q-1][i]>=0)
							    pix2_2[i]= pix_rgb2[q-1][i];
								else
								pix2_2[i]= 256+pix_rgb2[q-1][i];

								if(pix_rgb2[r-1][i]>=0)
								pix3_2[i]= pix_rgb2[r-1][i];
								else
								pix3_2[i]= 256+pix_rgb2[r-1][i];
							}
						    y_pass2=(Height-origin_y);//(num_of_slice-1));
							raster.setSamples(0,origin_y,x_win,y_pass2,0,pix1_2);
							raster.setSamples(0,origin_y,x_win,y_pass2,1,pix2_2);
							raster.setSamples(0,origin_y,x_win,y_pass2,2,pix3_2);
				 			raster.setSamples(0,origin_y,x_win,y_pass2,3,pix_2);

						}
						else
						{
							for( i=0;i<fsize2;i++)
							{
								pix_2[i]=255;

								if(pix_rgb2[0][i]>=0)
							    pix1_2[i]= pix_rgb2[0][i];
							    else
							    pix1_2[i]= 256+pix_rgb2[0][i];
							}
							pix3_2=pix2_2=pix1_2;

						    y_pass2=(Height-origin_y);//(num_of_slice-1));
							raster.setSamples(0,origin_y,x_win,y_pass2,0,pix1_2);
							raster.setSamples(0,origin_y,x_win,y_pass2,1,pix2_2);
							raster.setSamples(0,origin_y,x_win,y_pass2,2,pix3_2);
				 			raster.setSamples(0,origin_y,x_win,y_pass2,3,pix_2);

						}

						System.gc();

					}//close if(v==num_of_slice-1)


				}//close for "v"

			}// close else if
			else
			{

			  /* the code for 16 bit bigger image */
				line_start=0;
			  	System.out.println("Total number of slices : " + slices);
				for(int v=0;v<slices;v++)
				{
					System.out.println("\nSlice Number v :"+v);

					if(v!=(slices-1))
					{

				  		for( i=0;i<Bands;i++)
						{
							if(Bands==1 && i!=0)
							{
								continue;
							}

							//line_start=line_start2;
							line_start=v*y_win*Width*2;
							System.out.println("line start i :"+line_start+" "+i);
							System.out.println("\n");
							int k_index=0;
							// read the files line by line i.e x_win bytes in one go
							// and repeat unpo y_win i.e. size of windowx
							for(j=0;j<y_win;j++)
							{
								mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,line_start,x_win*2);
								mBuf.get(pix_line);// store each line in separate buffer
								for(k=0;k<x_win*2;k++,k_index++)
								{
									pix_rgb[i][k_index]=pix_line[k];

								}

								line_start=line_start+(Width*2);

							}//close for



				    	}//close for

						//line_start2=line_start;
							System.out.println("enter ");
							System.out.println("fsize :"+fsize);
							double TempCalc;
							int myChkVal;
						//	myChkVal = ((pix_rgb[0][1022*Width*2 + 560]<<8)|(pix_rgb[0][1022*Width*2 + 561]));
						System.out.println("Check value : p : " + p + ", q : " + q + ", r : " + r);
						if(Bands!=1)
						{
							line_start = 0;
							for( i=0;i<fsize;i++)
							{
				    		    pix[i]=255;
				    		    	//for pix1
								if(pix_rgb[p-1][2*i]<0)
									z=256+pix_rgb[p-1][2*i];
								else
									z=pix_rgb[p-1][2*i];

								if(pix_rgb[p-1][2*i+1]<0)
									g=256+pix_rgb[p-1][2*i+1];
								else
									g=pix_rgb[p-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix1[i] = p1 | p2;
								if (i==386400 || i==772800)
							    {//System.out.println(pix1[i]+" "+pix2[i])+" "+pix3[i];
								System.out.println(pix1[i]);
								//System.out.println(pix2[i]);
								//ystem.out.println(pix3[i]);
								}

								myTempCalc = ((double)pix1[i] / (double)myMax) * 255.0;
								pix1[i] = (int)myTempCalc;

							// for pix2
								if(pix_rgb[q-1][2*i]<0)
									z=256+pix_rgb[q-1][2*i];
								else
									z=pix_rgb[q-1][2*i];

								if(pix_rgb[q-1][2*i+1]<0)
									g=256+pix_rgb[q-1][2*i+1];
								else
									g=pix_rgb[q-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix2[i] = p1 | p2;
								if (i==386400 || i==772800)
							    {//System.out.println(pix1[i]+" "+pix2[i])+" "+pix3[i];
								//System.out.println(pix1[i]);
								System.out.println(pix2[i]);
								//ystem.out.println(pix3[i]);
								}

								myTempCalc = ((double)pix2[i] / (double)myMax) * 255.0;
								pix2[i] = (int)myTempCalc;

							// for pix3

								if(pix_rgb[r-1][2*i]<0)
									z=256+pix_rgb[r-1][2*i];
								else
									z=pix_rgb[r-1][2*i];

								if(pix_rgb[r-1][2*i+1]<0)
									g=256+pix_rgb[r-1][2*i+1];
								else
									g=pix_rgb[r-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix3[i] = p1 | p2;
								if (i==386400 || i==772800)
							    {//System.out.println(pix1[i]+" "+pix2[i])+" "+pix3[i];
								//System.out.println(pix1[i]);
								//System.out.println(pix2[i]);
								System.out.println(pix3[i]);
								}

								myTempCalc = ((double)pix3[i] / (double)myMax) * 255.0;
								pix3[i] = (int)myTempCalc;


				    		    line_start=line_start+Width*2;
				    		}

							raster.setSamples(0,origin_y,x_win,y_win,0,pix1);
							raster.setSamples(0,origin_y,x_win,y_win,1,pix2);
							raster.setSamples(0,origin_y,x_win,y_win,2,pix3);
				 			raster.setSamples(0,origin_y,x_win,y_win,3,pix);
				 			System.out.println("Main Slice : " + v);
							origin_x=0;
							origin_y=origin_y+y_win;
							System.gc();

					    }
						else
						{
							line_start = 0;
							for( i=0;i<fsize;i++)
							{
				    		    pix[i]=255;
				    		    	//for pix1
								if(pix_rgb[p-1][2*i]<0)
									z=256+pix_rgb[p-1][2*i];
								else
									z=pix_rgb[p-1][2*i];

								if(pix_rgb[p-1][2*i+1]<0)
									g=256+pix_rgb[p-1][2*i+1];
								else
									g=pix_rgb[p-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix1[i] = p1 | p2;
								if (i==386400 || i==772800)
							    {//System.out.println(pix1[i]+" "+pix2[i])+" "+pix3[i];
								System.out.println(pix1[i]);
								//System.out.println(pix2[i]);
								//ystem.out.println(pix3[i]);
								}

								myTempCalc = ((double)pix1[i] / (double)myMax) * 255.0;
								pix1[i] = (int)myTempCalc;

							// for pix2
								if(pix_rgb[q-1][2*i]<0)
									z=256+pix_rgb[q-1][2*i];
								else
									z=pix_rgb[q-1][2*i];

								if(pix_rgb[q-1][2*i+1]<0)
									g=256+pix_rgb[q-1][2*i+1];
								else
									g=pix_rgb[q-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix2[i] = p1 | p2;
								if (i==386400 || i==772800)
							    {//System.out.println(pix1[i]+" "+pix2[i])+" "+pix3[i];
								//System.out.println(pix1[i]);
								System.out.println(pix2[i]);
								//ystem.out.println(pix3[i]);
								}

								myTempCalc = ((double)pix2[i] / (double)myMax) * 255.0;
								pix2[i] = (int)myTempCalc;

							// for pix3

								if(pix_rgb[r-1][2*i]<0)
									z=256+pix_rgb[r-1][2*i];
								else
									z=pix_rgb[r-1][2*i];

								if(pix_rgb[r-1][2*i+1]<0)
									g=256+pix_rgb[r-1][2*i+1];
								else
									g=pix_rgb[r-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix3[i] = p1 | p2;
								myTempCalc = ((double)pix3[i] / (double)myMax) * 255.0;
								pix3[i] = (int)myTempCalc;


				    		    line_start=line_start+Width*2;
				    		}

							raster.setSamples(0,origin_y,x_win,y_win,0,pix1);
							raster.setSamples(0,origin_y,x_win,y_win,1,pix2);
							raster.setSamples(0,origin_y,x_win,y_win,2,pix3);
				 			raster.setSamples(0,origin_y,x_win,y_win,3,pix);
				 			System.out.println("Main Slice : " + v);
							origin_x=0;
							origin_y=origin_y+y_win;
							System.gc();

						}
						System.gc();

					}//close if(v!=num_of_slice-1)

					else if(v==(slices-1))
					{
						System.out.println("Last slice");
				  		for( i=0;i<Bands;i++)
						{
							if(Bands==1 && i!=0)
							{
								continue;
							}

							//line_start=line_start2;
							line_start=v*y_win*Width*2;
							System.out.println("line start i :"+line_start+" "+i);
							System.out.println("\n");
							int k_index=0;

							for(j=0;j<(Height-(slices-1)*y_win);j++)
							{
								mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,line_start,x_win*2);
								mBuf.get(pix_line);// store each line in separate buffer
								for(k=0;k<x_win*2;k++,k_index++)
								{
									pix_rgb2[i][k_index]=pix_line[k];

								}

								line_start=line_start+(Width*2);

							}//close for



				    	}//close for

						//line_start2=line_start;
							System.out.println("enter ");
							System.out.println("fsize :"+fsize);
							double TempCalc;
							int myChkVal;
						//	myChkVal = ((pix_rgb[0][1022*Width*2 + 560]<<8)|(pix_rgb[0][1022*Width*2 + 561]));
						System.out.println("Check value : p : " + p + ", q : " + q + ", r : " + r);
						if(Bands!=1)
						{
							line_start = 0;
							for( i=0;i<fsize2;i++)
							{

				    		    pix_2[i]=255;
				    		    	//for pix1
								if(pix_rgb2[p-1][2*i]<0)
									z=256+pix_rgb2[p-1][2*i];
								else
									z=pix_rgb2[p-1][2*i];

								if(pix_rgb2[p-1][2*i+1]<0)
									g=256+pix_rgb2[p-1][2*i+1];
								else
									g=pix_rgb2[p-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix1_2[i] = p1 | p2;

								myTempCalc = ((double)pix1_2[i] / (double)myMax) * 255.0;
								pix1_2[i] = (int)myTempCalc;

								// for pix2
								if(pix_rgb2[q-1][2*i]<0)
									z=256+pix_rgb2[q-1][2*i];
								else
									z=pix_rgb2[q-1][2*i];

								if(pix_rgb2[q-1][2*i+1]<0)
									g=256+pix_rgb2[q-1][2*i+1];
								else
									g=pix_rgb2[q-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix2_2[i] = p1 | p2;

								myTempCalc = ((double)pix2_2[i] / (double)myMax) * 255.0;
								pix2_2[i] = (int)myTempCalc;

								// for pix3

								if(pix_rgb2[r-1][2*i]<0)
									z=256+pix_rgb2[r-1][2*i];
								else
									z=pix_rgb2[r-1][2*i];

								if(pix_rgb2[r-1][2*i+1]<0)
									g=256+pix_rgb2[r-1][2*i+1];
								else
									g=pix_rgb2[r-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix3_2[i] = p1 | p2;

								myTempCalc = ((double)pix3_2[i] / (double)myMax) * 255.0;
								pix3_2[i] = (int)myTempCalc;

					    		    line_start=line_start+Width*2;
				    		}

				    		System.out.println("Displayed Image");

							raster.setSamples(0,origin_y,x_win,y_win2,0,pix1_2);
							raster.setSamples(0,origin_y,x_win,y_win2,1,pix2_2);
							raster.setSamples(0,origin_y,x_win,y_win2,2,pix3_2);
				 			raster.setSamples(0,origin_y,x_win,y_win2,3,pix_2);
				 			System.out.println("slice displayed1");
							origin_x=0;
							origin_y=origin_y+y_win;
							System.gc();

					    }
						else
						{
							 line_start = 0;
							for( i=0;i<fsize2;i++)
							{

				    		    pix_2[i]=255;
				    		    	//for pix1
								if(pix_rgb2[p-1][2*i]<0)
									z=256+pix_rgb2[p-1][2*i];
								else
									z=pix_rgb2[p-1][2*i];

								if(pix_rgb2[p-1][2*i+1]<0)
									g=256+pix_rgb2[p-1][2*i+1];
								else
									g=pix_rgb2[p-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix1_2[i] = p1 | p2;

								myTempCalc = ((double)pix1_2[i] / (double)myMax) * 255.0;
								pix1_2[i] = (int)myTempCalc;

								// for pix2
								if(pix_rgb2[q-1][2*i]<0)
									z=256+pix_rgb2[q-1][2*i];
								else
									z=pix_rgb2[q-1][2*i];

								if(pix_rgb2[q-1][2*i+1]<0)
									g=256+pix_rgb2[q-1][2*i+1];
								else
									g=pix_rgb2[q-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix2_2[i] = p1 | p2;

								myTempCalc = ((double)pix2_2[i] / (double)myMax) * 255.0;
								pix2_2[i] = (int)myTempCalc;

								// for pix3

								if(pix_rgb2[r-1][2*i]<0)
									z=256+pix_rgb2[r-1][2*i];
								else
									z=pix_rgb2[r-1][2*i];

								if(pix_rgb2[r-1][2*i+1]<0)
									g=256+pix_rgb2[r-1][2*i+1];
								else
									g=pix_rgb2[r-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix3_2[i] = p1 | p2;

								myTempCalc = ((double)pix3_2[i] / (double)myMax) * 255.0;
								pix3_2[i] = (int)myTempCalc;

					    		    line_start=line_start+Width*2;
				    		}

				    		System.out.println("Displayed Image");

							raster.setSamples(0,origin_y,x_win,y_win2,0,pix1_2);
							raster.setSamples(0,origin_y,x_win,y_win2,1,pix2_2);
							raster.setSamples(0,origin_y,x_win,y_win2,2,pix3_2);
				 			raster.setSamples(0,origin_y,x_win,y_win2,3,pix_2);
				 			System.out.println("slice displayed1");
							origin_x=0;
							origin_y=origin_y+y_win;
							System.gc();

						}
						System.gc();




					}//close if(v==num_of_slice-1)




			  }	//end of slice v==-1


			}


			for( i=0;i<Bands;i++)
			{
				fin_band[i].close();
				fchan_in[i].close();
			}
		}
		}
	    catch(Exception e)
	    {
	    	System.out.println(e);
	    }

		System.out.println("Hi");

	 	return img;
 	}


		/*raster.setSamples(0, 0, Width, Height, 0, pix1);
		raster.setSamples(0, 0, Width, Height, 1, pix2);
		raster.setSamples(0, 0, Width, Height, 2, pix3);
		raster.setSamples(0, 0, Width, Height, 3, pix);*/


}

class RatioSelection extends JFrame implements ActionListener
{
	public RatioSelection()
	{
		JRadioButton b1, b2, b3;
		System.out.println("Band Ratio selected");
		setTitle("Choose The Type Of Band Ratio To Be Used");
		setSize(400,200);
		setVisible(true);
		setLayout(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		if(DemoPanel.ratioType == 0)
		{
			b1 = new JRadioButton("Min / Max",true);
			b2 = new JRadioButton("(Max - Min) / (Max + Min)");
			b3 = new JRadioButton("TVR");
		}
		else if(DemoPanel.ratioType == 1)
		{
			b2 = new JRadioButton("(Max - Min) / (Max + Min)",true);
			b1 = new JRadioButton("Min / Max");
			b3 = new JRadioButton("TVR");
		}
		else
		{
			b1 = new JRadioButton("Min / Max");
			b2 = new JRadioButton("(Max - Min) / (Max + Min)");
			b3 = new JRadioButton("TVR",true);
		}
		JButton but = new JButton("Ok");
		JLabel lab = new JLabel("Select The Ratio You Want To Use");
		lab.setBounds(80,10,200,20);
		b1.setBounds(100,35,200,20);
		b2.setBounds(100,65,200,20);
		b3.setBounds(100,95,200,20);
		but.setBounds(150,135,100,30);
		add(lab);
		add(b1);
		add(b2);
		add(b3);
		add(but);
		but.addActionListener(this);
		ButtonGroup bg = new ButtonGroup();
		bg.add(b1);
		bg.add(b2);
		bg.add(b3);
		b1.setActionCommand("B1");
		b2.setActionCommand("B2");
		b3.setActionCommand("B3");
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
	}

	public static int getDigits(long num)
	{
		int c=0;
		while(num > 0)
		{
			num /= 10;
			c++;
		}
		return c;
	}

	public static int[][] multiply(int a[][], int m, int n, int b[][], int p, int q)
	{
		if(n != p)
		{
			System.out.println("Invalid size");
			return null;
		}
		int i, j, k;
		int c[][] = new int[m][q];
		for(i=0; i<m; i++)
		{
			for(j=0; j<q; j++)
			{
				c[i][j] = 0;
				for(k=0; k<n; k++)
					c[i][j] +=a[i][k] * b[k][j];
			}
		}
		return c;
	}

	public static int[] subtract(int a[], int b[], int n)
	{
		int c[] = new int[n];
		for(int i=0; i<n; i++)
			c[i] = a[i] - b[i];
		return c;
	}

	public static int[][] transpose(int a[][], int m, int n)
	{
		int r[][] = new int[n][m];
		for(int i=0; i<m; i++)
		{
			for(int j=0; j<n; j++)
				r[i][j] = a[j][i];
		}
		return r;
	}

	public void actionPerformed(ActionEvent ae)
	{
		if((ae.getActionCommand().equals("B3")))
		{
			DemoPanel.ratioType = 2;
		}
		else if((ae.getActionCommand().equals("B2")))
		{
			DemoPanel.ratioType = 1;
		}
		else if((ae.getActionCommand().equals("B1")))
		{
			DemoPanel.ratioType = 0;
		}
		else
			this.dispose();
		System.out.println(ae.getActionCommand()+ ", " + DemoPanel.ratioType);
	}
}



class CalcSelection extends JFrame implements ActionListener
{
	public CalcSelection()
	{
		JRadioButton b1, b2;
		System.out.println("Calculation Selection");
		setTitle("Choose The Type Of Calculation To Be Used");
		setSize(400,200);
		setVisible(true);
		setLayout(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		if(DemoPanel.calcType == 0)
		{
			b1 = new JRadioButton("On Click Basic",true);
			b2 = new JRadioButton("Manually");
		}
		else
		{
			b1 = new JRadioButton("On Click Basic");
			b2 = new JRadioButton("Manually",true);
		}
		JButton but = new JButton("Ok");
		JButton but2 = new JButton("Enter Values");
		JLabel lab = new JLabel("Select The Calculation Type You Want To Use");
		lab.setBounds(80,10,300,20);
		b1.setBounds(150,35,200,20);
		b2.setBounds(150,65,200,20);
		but.setBounds(80,100,120,30);
		but2.setBounds(200,100,120,30);
		add(lab);
		add(b1);
		add(b2);
		add(but);
		add(but2);
		but.addActionListener(this);
		but2.addActionListener(this);
		ButtonGroup bg = new ButtonGroup();
		bg.add(b1);
		bg.add(b2);
		b1.setActionCommand("B1");
		b2.setActionCommand("B2");
		b1.addActionListener(this);
		b2.addActionListener(this);
	}

	public void actionPerformed(ActionEvent ae)
	{
		String input;
		if((ae.getActionCommand().equals("B2")))
		{
			DemoPanel.calcType = 1;
		}
		else if((ae.getActionCommand().equals("B1")))
		{
			DemoPanel.calcType = 0;
		}
		else if((ae.getActionCommand().equals("Enter Values")))
		{
			System.out.println("Enter Clicked " + DemoPanel.count);
			DemoPanel.V = new int[DemoPanel.count];
			for(int i=0; i<DemoPanel.count; i++)
			{
				input = JOptionPane.showInputDialog("Please Enter The Value For Output : " + i);
				DemoPanel.V[i] = Integer.parseInt(input);
			}
		}
		else
		{
			if(DemoPanel.V == null && DemoPanel.calcType == 1)
			{
				JOptionPane.showMessageDialog(null,"Please Enter The Values","Message",JOptionPane.WARNING_MESSAGE);
				DemoPanel.V = new int[DemoPanel.count];
				for(int i=0; i<DemoPanel.count; i++)
				{
					input = JOptionPane.showInputDialog("Please Enter The Value For Output : " + i);
					DemoPanel.V[i] = Integer.parseInt(input);
				}
			}
			this.dispose();
		}
		System.out.println(ae.getActionCommand()+ ", " + DemoPanel.calcType);
	}
}

class BandSelection extends JFrame implements ActionListener
{
	public BandSelection()
	{
		JRadioButton b1, b2;
		System.out.println("Min / Max Band Selection");
		setTitle("Choose The Type Of Band Selection To Be Used");
		setSize(400,200);
		setVisible(true);
		setLayout(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		if(DemoPanel.bandType == 0)
		{
			b1 = new JRadioButton("On Click Basic",true);
			b2 = new JRadioButton("Manually");
		}
		else
		{
			b1 = new JRadioButton("On Click Basic");
			b2 = new JRadioButton("Manually",true);
		}
		JButton but = new JButton("Ok");
		JButton but2 = new JButton("Enter Values");
		JLabel lab = new JLabel("Select The Band Selection You Want To Use");
		lab.setBounds(80,10,300,20);
		b1.setBounds(150,35,200,20);
		b2.setBounds(150,65,200,20);
		but.setBounds(80,100,120,30);
		but2.setBounds(200,100,120,30);
		add(lab);
		add(b1);
		add(b2);
		add(but);
		add(but2);
		but.addActionListener(this);
		but2.addActionListener(this);
		ButtonGroup bg = new ButtonGroup();
		bg.add(b1);
		bg.add(b2);
		b1.setActionCommand("B1");
		b2.setActionCommand("B2");
		b1.addActionListener(this);
		b2.addActionListener(this);
	}

	public void actionPerformed(ActionEvent ae)
	{
		String input;
		if((ae.getActionCommand().equals("B2")))
		{
			DemoPanel.bandType = 1;
		}
		else if((ae.getActionCommand().equals("B1")))
		{
			DemoPanel.bandType = 0;
		}
		else if((ae.getActionCommand().equals("Enter Values")))
		{
			int tempVal=-1;
			System.out.println("Enter Clicked " + DemoPanel.count);
			while(tempVal == -1)
			{
				input = JOptionPane.showInputDialog("Please Enter The Value For Minimum Band");
				tempVal = Integer.parseInt(input);
				System.out.println("Bands : " + DemoPanel.bands);
				if(tempVal < 0 || tempVal >= DemoPanel.bands)
				{
					JOptionPane.showMessageDialog(null,"Please Enter The Valid Band Number","Message",JOptionPane.WARNING_MESSAGE);
					tempVal = -1;
				}
				else
					DemoPanel.minIndex = Integer.parseInt(input);
			}
			tempVal = -1;
			while(tempVal == -1)
			{
				input = JOptionPane.showInputDialog("Please Enter The Value For Maximum Band");
				tempVal = Integer.parseInt(input);
				if(tempVal < 0 || tempVal >= DemoPanel.bands)
				{
					JOptionPane.showMessageDialog(null,"Please Enter The Valid Band Number","Message",JOptionPane.WARNING_MESSAGE);
					tempVal = -1;
				}
				else
					DemoPanel.maxIndex = Integer.parseInt(input);
			}
		}
		else
		{
			/*if(DemoPanel.minIndex == DemoPanel.maxIndex)
			{
				JOptionPane.showMessageDialog(null,"Please Enter The Valid Band Numbers","Message",JOptionPane.WARNING_MESSAGE);
			}*/
			this.dispose();
		}
		System.out.println(ae.getActionCommand()+ ", " + DemoPanel.bandType);
	}
}

class DemoPanel extends JPanel implements MouseMotionListener
{
	static BufferedImage Imag;
	static String ImageName[] = new String[50];
	static int count = 0;
	private Dimension ViewSize;
	private picture Picture1;
	private ReadImageData ImgData1;
	static int ratioType = 0;
	static int calcType = 0;
	static int bandType = 0;
	static int clickType = 0;
	static int XArr[] = new int[100];
	static int YArr[] = new int[100];
	static int clickCount = 0;

	static int V[];
	static double EFactor = 1.0;

	static int x;
	static int y;
	static int width;
	static int height;
	static int bands;
	static int shiftFlag;
	static int maxIndex, minIndex, minVal, maxVal;
	//	private DialogDemo message;
	private static int Noofclicks;
	//	private calculate c1;
	public DemoPanel()
	{
	}

	public BufferedImage getpixdata()
	{
		return Imag;
	}
	int return_x()
	{
		if (x >= 0)
			return x;
		else
			return -1;

	}
	int return_y()
	{
		if (y >= 0)
			return y;
		else
			return -1;
	}

	public static void setImageName(String FNam)
	{
		ImageName[count++] = FNam;
	}

	public DemoPanel(BufferedImage Img)
	{
		System.gc();

		this.Imag = Img;
		int Width = Math.min(256, Imag.getWidth());
		int Height = Math.min(256, Imag.getHeight());
		ViewSize = new Dimension(Width, Height);
		setPreferredSize(new Dimension(Imag.getWidth(), Imag.getHeight()));
		x = y = 0;
		addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent me)
			{
				try
				{
					File fn = new File(ImageName[0]);
					FileInputStream f1 = new FileInputStream(fn);
					FileChannel fchan = f1.getChannel();
					ImgData1 = new ReadImageData();
					ImgData1.read(fn);
					width = ImgData1.columns();
					height = ImgData1.rows();
					bands = ImgData1.bands();
				}
				catch(Exception e)
				{

				}
				int k, i;
				int tmpX=0, tmpY=0;
				char temp[] = new char[10];
				char temp2[] = new char[100];
				int myFlag = 0;
				if(clickCount == 0)
				{
					tmpX = (int)(me.getX() / DemoPanel.EFactor);
					tmpY = (int)(me.getY() / DemoPanel.EFactor);
					if(tmpX > width || tmpY > height || tmpX < 0 || tmpY < 0)
					{
						JOptionPane.showMessageDialog(null, "You have clicked outside the image", "Warning", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					XArr[0] = x = tmpX;
					YArr[0] = y = tmpY;
				}
				if(clickType == 0)
				{
					tmpX = (int)(me.getX() / DemoPanel.EFactor);
					tmpY = (int)(me.getY() / DemoPanel.EFactor);
					if(tmpX > width || tmpY > height || tmpX < 0 || tmpY < 0)
					{
						JOptionPane.showMessageDialog(null, "You have clicked outside the image", "Warning", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					XArr[0] = x = tmpX;
					YArr[0] = y = tmpY;
				}
				else
				{
					tmpX = (int)(me.getX() / DemoPanel.EFactor);
					tmpY = (int)(me.getY() / DemoPanel.EFactor);
					if(tmpX > width || tmpY > height || tmpX < 0 || tmpY < 0)
					{
						JOptionPane.showMessageDialog(null, "You have clicked outside the image", "Warning", JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					XArr[clickCount] = tmpX;
					YArr[clickCount] = tmpY;
					clickCount++;
				}
				System.out.println("Mouse Clicked at (" + x + "," + y + ")");
				System.out.println("Name : " + ImageName[0]);

				if(clickType == 1)
				{
					System.out.println("Clicked Array:");
					for(i=0; i<clickCount; i++)
						System.out.println("X: " + XArr[i] + ", Y: " + YArr[i]);
				}

				try
				{

					File fn = new File(ImageName[0]);
					FileInputStream f1 = new FileInputStream(fn);
					FileChannel fchan = f1.getChannel();
					ImgData1 = new ReadImageData();
					ImgData1.read(fn);
					width = ImgData1.columns();
					height = ImgData1.rows();
					bands = ImgData1.bands();

					try
					{
						InputStream MyHdr = new FileInputStream(ImageName[0] + ".hdr");
						for (k = 0; k < 71; k++)
							temp2[k] = (char)MyHdr.read();
						for (k = 71; k < 78; k++)
						{
							temp[k - 71] = (char)MyHdr.read();
						}
					}
					catch (Exception e) { myFlag = 1; }
					String myTemp = (new String(temp)).trim();

					try
					{
						String s = myTemp.substring(1);
						shiftFlag = Integer.parseInt (s);
					}
					catch(Exception e){ myFlag = 1;}

					if(myFlag == 1)
						shiftFlag = 8;

					int tempWidth=0, tempX=0, nob=0;
					if(shiftFlag == 8)
					{
						tempX = x;
						tempWidth = width;
						nob = 1;
					}
					else
					{
						tempX = x * 2;
						tempWidth = width * 2;
						nob = 2;
					}

					MappedByteBuffer mBuf;
					int bvalues[] = new int[bands];
					long lstart;
					byte b1, b2;
					int p1, p2;

					for(i=0; i<bands; i++)
					{
						lstart = (y * tempWidth * bands) + (i * tempWidth) + tempX;
						mBuf = fchan.map(FileChannel.MapMode.READ_ONLY, lstart, nob);
						if(shiftFlag == 8)
						{
							b1 = mBuf.get();
							bvalues[i] = (b1<<24) >>> 24;
						}
						else
						{
							b1 = mBuf.get();
							b2 = mBuf.get();
							p1 = b1 << 24;
							p2 = (b2 <<24 ) >>> 8;
							bvalues[i] = p1 | p2;
							bvalues[i] = bvalues[i] >>> 16;

						}
						System.out.println("Value stored at Band No. " + i + ", " + bvalues[i]);
					}

					minIndex = maxIndex = 0;
					minVal = maxVal = bvalues[0];
					for(i=1; i<bands; i++)
					{
						if(bvalues[i] > maxVal)
						{
							maxVal = bvalues[i];
							maxIndex = i;
						}
						if(bvalues[i] < minVal)
						{
							minVal = bvalues[i];
							minIndex = i;
						}
					}

					double calcRatio = 0.0;
					System.out.println("Maximum : " + maxVal + " at " + maxIndex);
					System.out.println("Minimum : " + minVal + " at " + minIndex);
					if(ratioType == 0)
						System.out.println("Band Ratio for first file : " + ((double)minVal / (double)maxVal));
					else if(ratioType == 1)
					{
						calcRatio = ((double)(maxVal - minVal) / (double)(maxVal + minVal));
						if(calcRatio < 0.0)
							calcRatio = 0.0;
						if(calcRatio > 1.0)
							calcRatio = 1.0;
						System.out.println("Band Ratio for first file : " + calcRatio);
					}
					else
					{
						calcRatio = ((double)(maxVal - minVal) / (double)(maxVal + minVal)) + 0.5;
						if(calcRatio < 0.0)
							calcRatio = 0.0;
						calcRatio = Math.sqrt(calcRatio);
						if(calcRatio < 0.0)
							calcRatio = 0.0;
						if(calcRatio > 1.0)
							calcRatio = 1.0;
						System.out.println("Band Ratio for first file : " + calcRatio);
					}
					System.out.println("\nBand Ratio for other files:");
					for(k=1; k<count; k++)
					{
						System.out.println("\n" + k + "): " + ImageName[k]);
						fn = new File(ImageName[k]);
						f1 = new FileInputStream(fn);
						fchan = f1.getChannel();
						//Finding min and max bands
						for(i=0; i<bands; i++)
						{
							lstart = (y * tempWidth * bands) + (i * tempWidth) + tempX;
							mBuf = fchan.map(FileChannel.MapMode.READ_ONLY, lstart, nob);
							if(shiftFlag == 8)
							{
								b1 = mBuf.get();
								bvalues[i] = (b1<<24) >>> 24;
							}
							else
							{
								b1 = mBuf.get();
								b2 = mBuf.get();
								p1 = b1 << 24;
								p2 = (b2 <<24 ) >>> 8;
								bvalues[i] = p1 | p2;
								bvalues[i] = bvalues[i] >>> 16;

							}
							System.out.println("Value stored at Band No. " + i + ", " + bvalues[i]);
						}

						minIndex = maxIndex = 0;
						minVal = maxVal = bvalues[0];
						for(i=1; i<bands; i++)
						{
							if(bvalues[i] > maxVal)
							{
								maxVal = bvalues[i];
								maxIndex = i;
							}
							if(bvalues[i] < minVal)
							{
								minVal = bvalues[i];
								minIndex = i;
							}
						}


						//Reading min values
						lstart = (y * tempWidth * bands) + (minIndex * tempWidth) + tempX;
						mBuf = fchan.map(FileChannel.MapMode.READ_ONLY, lstart, nob);
						if(shiftFlag == 8)
						{
							b1 = mBuf.get();
							minVal = (b1<<24) >>> 24;
						}
						else
						{
							b1 = mBuf.get();
							b2 = mBuf.get();
							p1 = b1 << 24;
							p2 = (b2 <<24 ) >>> 8;
							minVal = p1 | p2;
							minVal = minVal >>> 16;
						}

						//Reading max values
						lstart = (y * tempWidth * bands) + (maxIndex * tempWidth) + tempX;
						mBuf = fchan.map(FileChannel.MapMode.READ_ONLY, lstart, nob);
						if(shiftFlag == 8)
						{
							b1 = mBuf.get();
							maxVal = (b1<<24) >>> 24;
						}
						else
						{
							b1 = mBuf.get();
							b2 = mBuf.get();
							p1 = b1 << 24;
							p2 = (b2 <<24 ) >>> 8;
							maxVal = p1 | p2;
							maxVal = maxVal >>> 16;
						}

						System.out.println("Minimum : " + minVal + ", Maximum : " + maxVal);
						if(ratioType == 0)
							System.out.println("Band Ratio for first file : " + ((double)minVal / (double)maxVal));
						else if(ratioType == 1)
						{
							calcRatio = ((double)(maxVal - minVal) / (double)(maxVal + minVal));
							if(calcRatio < 0.0)
								calcRatio = 0.0;
							if(calcRatio > 1.0)
								calcRatio = 1.0;
							System.out.println("Band Ratio for first file : " + calcRatio);
						}
						else
						{
							calcRatio = ((double)(maxVal - minVal) / (double)(maxVal + minVal)) + 0.5;
							if(calcRatio < 0.0)
								calcRatio = 0.0;
							calcRatio = Math.sqrt(calcRatio);
							if(calcRatio < 0.0)
								calcRatio = 0.0;
							if(calcRatio > 1.0)
								calcRatio = 1.0;
							System.out.println("Band Ratio for first file : " + calcRatio);
						}
					}


				}
				catch(Exception e)
				{
					System.out.println("Mouse Prob: " + e);
				}

			}


		});

		addMouseMotionListener(this);

	}

	public void mouseDragged(MouseEvent me)
	{
	}

	public void mouseMoved(MouseEvent me)
	{
		setToolTipText("(X:" + me.getX() + ", Y:" + me.getY() + ")");
		repaint();
	}


	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(Imag, 0, 0, this);
		g2.dispose();
	}


}
class AboutDialog extends JDialog implements ActionListener
{
	static int Band1 = 4;
	static int Band2 = 4;
	static int Band3 = 4;
	private ReadImageData ImgData2;
	Container contentPane = getContentPane();
	JLabel pict, pict1, pict2;
	JTextField f1, f2, f3;
	JButton b1, b2;
	JComboBox list, list1, list2;
	int x, y, z;
	private boolean ok;

	public int show_band1()
	{
		return Band1;
	}
	public int show_band2()
	{
		return Band2;
	}
	public int show_band3()
	{
		return Band3;
	}
	public AboutDialog(JFrame parent)
	{
		super(parent, "Bands Chooser", true);
		ImgData2 = new ReadImageData();
		int i = ImgData2.bands();
		String str[] = new String[i];
		GridBagLayout g1;
		GridBagConstraints gbc;
		int k = i;
		for (int j = 0; j < i; j++)
		{
			str[j] = Integer.toString(k);
			k--;
		}
		b1 = new JButton("Ok");
		b2 = new JButton("Cancel");
		list = new JComboBox(str);
		list1 = new JComboBox(str);
		list2 = new JComboBox(str);
		pict = new JLabel("Red");
		pict1 = new JLabel("Green");
		pict2 = new JLabel("Blue");

		f1 = new JTextField(5);
		f1.setBackground(Color.red);
		f1.setEnabled(false);
		f2 = new JTextField(5);
		f2.setBackground(Color.green);
		f2.setEnabled(false);
		f3 = new JTextField(5);
		f3.setBackground(Color.blue);
		f3.setEnabled(false);


		g1 = new GridBagLayout();
		contentPane.setLayout(g1);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 0;
		gbc.gridy = 0;
		g1.setConstraints(pict, gbc);
		contentPane.add(pict);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 2;
		gbc.gridy = 0;

		g1.setConstraints(f1, gbc);
		contentPane.add(f1);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 4;
		gbc.gridy = 0;

		g1.setConstraints(list, gbc);
		contentPane.add(list);
		//list.addActionListener(this);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 6;//4
		gbc.gridy = 0;
		g1.setConstraints(pict1, gbc);
		contentPane.add(pict1);
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 8;//6
		gbc.gridy = 0;

		g1.setConstraints(f2, gbc);
		contentPane.add(f2);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 10;
		gbc.gridy = 0;

		g1.setConstraints(list1, gbc);
		contentPane.add(list1);
		//list1.addActionListener(this);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 12;//8
		gbc.gridy = 0;
		g1.setConstraints(pict2, gbc);
		contentPane.add(pict2);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 14;//10
		gbc.gridy = 0;


		g1.setConstraints(f3, gbc);
		contentPane.add(f3);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 16;//12
		gbc.gridy = 0;

		g1.setConstraints(list2, gbc);
		contentPane.add(list2);
		//list2.addActionListener(this);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = 2;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 3;
		gbc.gridy = 2;
		g1.setConstraints(b1, gbc);
		contentPane.add(b1);
		b1.addActionListener(this);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = 2;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 6;
		gbc.gridy = 2;
		g1.setConstraints(b2, gbc);
		contentPane.add(b2);
		b2.addActionListener(this);
		dispose();
		//System.gc();
	}
	public void actionPerformed(ActionEvent evt)
	{
		Object source = evt.getSource();
		if (source == b1)//
		{
			String s = (String)list.getSelectedItem();
			String s1 = (String)list1.getSelectedItem();
			String s2 = (String)list2.getSelectedItem();
			int x = Integer.parseInt(s);
			this.Band1 = x;
			int y = Integer.parseInt(s1);
			this.Band2 = y;
			int z = Integer.parseInt(s2);
			this.Band3 = z;
			dispose();
		}
		else
		{
			dispose();
		}
	}
}





class Enhancement
{
	private ReadImageData imgdata;
	private calculate cal;
	BufferedImage img;
	int [][][] pixeldata;
	double [][][] pixeldata1;
	int Bands;
	int Height;
	int Width;
	picture pict=new picture();

	public Enhancement()
	{}

	public Enhancement(int i,int j,int k)
	{
		pict=new picture();
		img=pict.getpicdata(i,j,k);
	}

	public void enhances(int col1,int col2,int col3)
	{
		cal=new calculate();
		imgdata=new ReadImageData();
		double min=0;
		double max=0;
		int p=col1;
		int q=col2;
		int r=col3;
		int index=0;
		Bands=imgdata.bands();
		Width=imgdata.columns();
		Height=imgdata.rows();
		int pix[] = new int [(Width)*(Height)];
		int pix1[] = new int [(Width)*(Height)];
		int pix2[] = new int [(Width)*(Height)];
		int pix3[] = new int [(Width)*(Height)];
		if(Bands!=1)
			pixeldata1=new double[Bands][Height][Width];
		else
			pixeldata1=new double[3][Height][Width];
		pixeldata=new int[Bands][Height][Width];
		pixeldata=cal.retpix();
		MappedByteBuffer mBuf;
		long fsize=Height*Width;
		FileChannel fchan_in[]=new FileChannel[Bands];
		byte [][]pix_rgb=new byte[Bands][(int)fsize];
		byte [][]pix_rgb1=new byte[Bands][(int)fsize*2];
		FileInputStream fin_band[]=new FileInputStream[Bands];

 		try
 		{

 			for(int i=0;i<Bands;i++)
 			{
 				fin_band[i] = new FileInputStream("Data" + picture.ImageName +  i);
 				fchan_in[i] = fin_band[i].getChannel();
 			}

 		}
 		catch(Exception e)
 		{
 			System.out.println("Ex:"+e);
 		}
		int temp,tempCount,p1,p2;;
		int i=0,j,k,x,l;
		int r1=(BilImage.p)-1;
		int g1=(BilImage.q)-1;
		int b1=(BilImage.r)-1;
 		int b[][]=new int[Bands*Height][Width];
 		int a[][][];
 		if(Bands!=1)
 		 	a=new int [Bands][Height][Width*2];
 		else
 			a=new int [3][Height][Width*2];
		try
		{
			if(Bands!=1)
			{
				if(MyHdrFile.bits==8)
				{

					mBuf=fchan_in[r1].map(FileChannel.MapMode.READ_ONLY,0,fsize);
					mBuf.get(pix_rgb[i]);
					for(j=0;j<Height;j++)
						for(k=0;k<Width;k++)
						{
							temp=(int)pix_rgb[i][j*(Width)+k];
							if(temp<0)
								a[0][j][k]=255+(int)pix_rgb[i][j*(Width)+k];
							else
								a[0][j][k]=(int)pix_rgb[i][j*(Width)+k];
						}
					mBuf=fchan_in[g1].map(FileChannel.MapMode.READ_ONLY,0,fsize);
					mBuf.get(pix_rgb[i]);
					for(j=0;j<Height;j++)
						for(k=0;k<Width;k++)
						{
							temp=(int)pix_rgb[i][j*(Width)+k];
							if(temp<0)
								a[1][j][k]=255+(int)pix_rgb[i][j*(Width)+k];
							else
								a[1][j][k]=(int)pix_rgb[i][j*(Width)+k];
						}
						System.out.println("Before Error g1 : " + b1);
					mBuf=fchan_in[b1].map(FileChannel.MapMode.READ_ONLY,0,fsize);

					mBuf.get(pix_rgb[i]);

					for(j=0;j<Height;j++)
						for(k=0;k<Width;k++)
						{
							temp=(int)pix_rgb[i][j*(Width)+k];
							if(temp<0)
								a[2][j][k]=255+(int)pix_rgb[i][j*(Width)+k];
							else
								a[2][j][k]=(int)pix_rgb[i][j*(Width)+k];
						}
					System.gc();
				}
				else
				{
					System.out.println("bigin");
					System.gc();
					mBuf=fchan_in[r1].map(FileChannel.MapMode.READ_ONLY,0,fsize*2);
					mBuf.get(pix_rgb1[0]);
					for(j=0;j<Height-1;j++)
						for(k=0;k<Width*2;k++)
						{
							p1 = pix_rgb1[0][2*(j*Width+k)] << 24;
							p2 = (pix_rgb1[0][2*(j*Width+k)+1] <<24 ) >>> 8;
							tempCount = p1 | p2;
							tempCount = tempCount >>> 16;
							a[0][j][k]=tempCount;
						}

					mBuf=fchan_in[g1].map(FileChannel.MapMode.READ_ONLY,0,fsize*2);
					mBuf.get(pix_rgb1[0]);
					for(j=0;j<Height-1;j++)
						for(k=0;k<Width*2;k++)
						{
							p1 = pix_rgb1[0][2*(j*Width+k)] << 24;
							p2 = (pix_rgb1[0][2*(j*Width+k)+1] <<24 ) >>> 8;
							tempCount = p1 | p2;
							tempCount = tempCount >>> 16;
							a[1][j][k]=tempCount;
						}

					mBuf=fchan_in[b1].map(FileChannel.MapMode.READ_ONLY,0,fsize*2);
					mBuf.get(pix_rgb1[0]);
					for(j=0;j<Height-1;j++)
						for(k=0;k<Width*2;k++)
						{
							p1 = pix_rgb1[0][2*(j*Width+k)] << 24;
							p2 = (pix_rgb1[0][2*(j*Width+k)+1] <<24 ) >>> 8;
							tempCount = p1 | p2;
							tempCount = tempCount >>> 16;
							a[2][j][k]=tempCount;
						}
					System.out.println("end");
				}
			}
			else
			{
			    FileInputStream fin=new FileInputStream(DemoPanel.ImageName[0]);
			    FileChannel fchan_in1[]=new FileChannel[3];
	            fchan_in1[0]=fin.getChannel();
	            fchan_in1[1]=fin.getChannel();
	            fchan_in1[2]=fin.getChannel();

	            i=0;
	            mBuf=null;
	            try
	            {
					mBuf=fchan_in1[0].map(FileChannel.MapMode.READ_ONLY,0,fsize);

					mBuf.get(pix_rgb[i]);

					for(j=0;j<Height;j++)
						for(k=0;k<Width;k++)
						{
							temp=(int)pix_rgb[i][j*(Width)+k];
							if(temp<0)
								a[0][j][k]=255+(int)pix_rgb[i][j*(Width)+k];
							else
								a[0][j][k]=(int)pix_rgb[i][j*(Width)+k];
						}
					max=pict.maxBand[0];
					min=pict.minBand[0];
					for(int i1=0;i1<3;i1++)
						for(j=0;j<Height;j++)
     	   					for( k=0;k<Width;k++)
        	   					pixeldata1[i1][j][k]=((a[0][j][k]-min)/(max-min))*255;
	           	}
				catch(Exception e)
				{
					System.out.println("1 Band Enhance Error : "+e);
				}
				System.gc();
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception1 : "+e);
		}

// Finding max & min of displaying Bands.

		if(Bands!=1)
		{
		 	for(i=0;i<3;i++)
		 	{
		 		if(i==0)
		 		{
		 			max=pict.maxBand[r1];
		 			min=pict.minBand[r1];
		 		}
		 		if(i==1)
		 		{
		 			max=pict.maxBand[g1];
		 			min=pict.minBand[g1];
		 		}
		 		if(i==2)
		 		{
		 			max=pict.maxBand[b1];
		 			min=pict.minBand[b1];
		 		}
		 		for(j=0;j<Height;j++)
         			for( k=0;k<Width;k++)
           				pixeldata1[i][j][k]=((a[i][j][k]-min)/(max-min))*255;
			}
		}

     	for(i=0;i<Height;i++)
        {
        	for ( j=0;j<Width;j++)
	    	{
		 		pix[index]=255;
	     		pix1[index]= (int)pixeldata1[0][i][j];
		 		pix2[index]= (int)pixeldata1[1][i][j];
		 		pix3[index]= (int)pixeldata1[2][i][j];
		 		index++;
			}
        }

		img=new BufferedImage(Width,Height,BufferedImage.TYPE_INT_ARGB);
		WritableRaster raster=img.getRaster();
		raster.setSamples(0,0,Width,Height,0,pix1);
		raster.setSamples(0,0,Width,Height,1,pix2);
		raster.setSamples(0,0,Width,Height,2,pix3);
		raster.setSamples(0,0,Width,Height,3,pix);

		System.out.println("Image Enhanced");

	}

	public BufferedImage getpicdata()
	{
		return img;
	}
}


class calculate
{

	static Vector pixelValue=new Vector();   				//vector for capture pixel value

	private DemoPanel demo;
	private BufferedImage image;
	private ReadImageData imgdat11;
    static int Bands;
    static int Width;
    static int Height;


    Double pixelArray[];
    static double pixel_Array[];		//arrary for geting vector values
    static int[]xy=new int[2];                  //for tranfering x,y coordinate for accuracy assesment


	int bands;
	private ZoomIn zoom1;
	private static int newsize=1;
	private ReadImageData imgdat;
	static double bandsdata[]=new double[Bands];
	static int[][][] pixvalue=new int[Bands][Width][Height];
	public calculate()
	{
	}
	public calculate(int a[][][])
	{
	  imgdat=new ReadImageData();
	  this.Bands=imgdat.bands();
      this.Width=imgdat.columns();
      this.Height=imgdat.rows();
      this.pixvalue=a;
	  double bandsdat[]=new double[Bands];
	  this.bandsdata=bandsdat;
	  for(int i=0;i<Bands;i++)
	    bandsdata[i]=0.00000000000;
	}

	public  calculate(int x,int y)
    {
	  imgdat11=new ReadImageData();
	  int bands=imgdat11.bands();
	  this.Bands=imgdat11.bands();
	  zoom1=new ZoomIn();
	  this.newsize=zoom1.retnewsize();
	  int newx=(int)(x/newsize);
	  int newy=(int)(y/newsize);
	  double banddata[]=new double[bands];
      xy[0]=newx;
      xy[1]=newy;

	  for(int i=0;i<Bands;i++)
	    banddata[i]=pixvalue[i][newy][newx];			//for tranfring value to acuracy class


	// code for storing pixel values

	try
	{


	   for(int i=0;i<Bands;i++)
	   {
	    	Double temp=new Double(banddata[i]);
	   		pixelValue.addElement(temp);                     			        //adding elements to vector array
	   }

       int l=pixelValue.size();
       pixelArray=new Double[l];
       pixel_Array=new double[l];
       pixelValue.copyInto(pixelArray);

       for(int m=0;m<l;m++)
       {

       	pixel_Array[m]=pixelArray[m].doubleValue();     //converting double object to primitive double**/
       	System.out.println(pixel_Array[m]);
       }

	}
	catch(Exception e)
	{
		System.out.println("Bhoopendra Chauhan"+  e);
	}
		for(int d=0;d<Bands;d++)
	   		this.bandsdata[d]=banddata[d]+this.bandsdata[d];
  }


  public double[] retbandsdata()// return the total of pixel values of coordinates clicked
  {
    return bandsdata;
  }


 public int[][][] retpix()      /** return the values of pixvalues matrix **/
  {
    return pixvalue;
  }

 public int[]retxy()            // to return x,y coordinate
  {
    return xy;
  }



  public double[] ret_pixelArray()			// function to return pixel values
  {
  	return pixel_Array;
  }

  public void remove()                          // function to remove vector elements
   {
    int i=pixelValue.size();
    //System.out.println(i);
    for(int j=0;j<i;j++)
       {
        Double t=new Double(pixel_Array[j]);

        pixelValue.removeElement(t);

        }
  }


/************* Function For Calculating Error Function *********************************************/


public  double erf(double z)
 {
        double t = 1.0 / (1.0 + 0.5 * Math.abs(z));

        // use Horner's method
        double ans = 1 - t * Math.exp( -z*z   -   1.26551223 +
                                            t * ( 1.00002368 +
                                            t * ( 0.37409196 +
                                            t * ( 0.09678418 +
                                            t * (-0.18628806 +
                                            t * ( 0.27886807 +
                                            t * (-1.13520398 +
                                            t * ( 1.48851587 +
                                            t * (-0.82215223 +
                                            t * ( 0.17087277))))))))));
        if (z >= 0) return  ans;
        else        return -ans;
    }

}






class ZoomIn
 {
    private BufferedImage picture;
	private static int newsize=1;
	public ZoomIn ()
	{
	}
	public ZoomIn (BufferedImage Imag,int Enlargefactor)
	{
	  System.gc();
	  int w=Imag.getWidth()*Enlargefactor;
	  int h=Imag.getHeight()*Enlargefactor;
	  BufferedImage EnlargedImag=new BufferedImage(w,h,Imag.getType());
	  for(int y=0;y < h;y++)
	  for(int x=0;x < w;x++)
	     EnlargedImag.setRGB(x,y,Imag.getRGB(x/Enlargefactor,y/Enlargefactor));
	  System.out.println(EnlargedImag.getWidth());
	  this.picture=EnlargedImag;
	  this.newsize=Enlargefactor;
    }

      public int retnewsize()
	  {
	     return newsize;
       }
      public BufferedImage retchangepic()
	   {
	    return picture;
       }
}




 class ZoomOut
 {
    private BufferedImage picture;
	public ZoomOut(BufferedImage Imag,int Enlargefactor)
	{
	  System.gc();
	  int w=Imag.getWidth()/Enlargefactor;
	  int h=Imag.getHeight()/Enlargefactor;
	  BufferedImage EnlargedImag=new BufferedImage(w,h,Imag.getType());
	  for(int y=0;y < h;y++)
	  for(int x=0;x < w;x++)
	     EnlargedImag.setRGB(x,y,Imag.getRGB(x*Enlargefactor,y*Enlargefactor));
		 this.picture=EnlargedImag;
     }
	  public BufferedImage retchangepic()
	   {
	    return picture;
       }

}


class MyHdrFile
{
	public static File f;
	public static int bands;
	public static int rows,cols;
	public static int bits;

	/*MyHdrFile(File file)
	{
		String str=file.getName();
		f=file;

	}*/

	public static void browse(File myFile)
	{
		f = myFile;
		char c[]=new char[78];
		char temp[]=new char[10];
		int i,j;
		InputStream f1;
		try
		{
			f1=new FileInputStream( f + ".hdr");
			for(i=0;i<78;i++)
			{
				c[i]=(char)f1.read();
			}
			f1.close();
		}
		catch(Exception e){}

		for(i=5,j=0;i<14;i++)
		{
			if(c[i]>= '0' && c[i] <='9')
			{
				temp[j]=c[i];
				j++;
			}
		}
		String str=new String(temp);
		bands=Integer.parseInt(str.trim());

		clean(temp);
		j=0;
		for(i=19; i<28; i++)
		{
			if(c[i]>= '0' && c[i] <='9')
			{
				temp[j]=c[i];
				j++;
			}
		}
		str = new String(temp);
		rows = Integer.parseInt(str.trim());

		clean(temp);
		j=0;
		for(i=33; i<42; i++)
		{
			if(c[i]>= '0' && c[i] <='9')
			{
				temp[j]=c[i];
				j++;
			}
		}
		str = new String(temp);
		cols = Integer.parseInt(str.trim());

		clean(temp);
		j=0;
		for(i=73; i<76; i++)
		{
			if(c[i]>= '0' && c[i] <='9')
			{
				temp[j]=c[i];
				j++;
			}
		}
		str = new String(temp);
		bits = Integer.parseInt(str.trim());

		System.out.println("Bands : " + bands);
		System.out.println("Rows : " + rows);
		System.out.println("Cols : " + cols);
		System.out.println("DataType : " + bits);
	}

	static void clean(char a[])
	{
		for(int i=0; i<a.length; i++)
			a[i] = ' ';
	}

}


class ClickSelection extends JFrame implements ActionListener
{
	public ClickSelection()
	{
		JRadioButton b1, b2;
		System.out.println("Click Selection");
		setTitle("Choose The Type Of Clicks To Be Used");
		setSize(400,200);
		setVisible(true);
		setLayout(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		if(DemoPanel.calcType == 0)
		{
			b1 = new JRadioButton("Single Click",true);
			b2 = new JRadioButton("Multiple Clicks");
		}
		else
		{
			b1 = new JRadioButton("Single Click");
			b2 = new JRadioButton("Multiple Clicks",true);
		}
		JButton but = new JButton("Ok");
		JLabel lab = new JLabel("Select The Click Type You Want To Use");
		lab.setBounds(80,10,300,20);
		b1.setBounds(150,35,200,20);
		b2.setBounds(150,65,200,20);
		but.setBounds(150,100,120,30);
		add(lab);
		add(b1);
		add(b2);
		add(but);
		but.addActionListener(this);
		ButtonGroup bg = new ButtonGroup();
		bg.add(b1);
		bg.add(b2);
		b1.setActionCommand("B1");
		b2.setActionCommand("B2");
		b1.addActionListener(this);
		b2.addActionListener(this);
	}

	public void actionPerformed(ActionEvent ae)
	{
		String input;
		if((ae.getActionCommand().equals("B2")))
		{
			DemoPanel.clickType = 1;
		}
		else if((ae.getActionCommand().equals("B1")))
		{
			DemoPanel.clickType = 0;
		}
		else
		{
			this.dispose();
		}
		System.out.println(ae.getActionCommand()+ ", " + DemoPanel.clickType);
	}
}
