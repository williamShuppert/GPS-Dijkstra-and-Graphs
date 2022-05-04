import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

public class UI extends JPanel {
	JFrame window = new JFrame("GPS - Dijkstra and Graphs");
	Timer tmr = null;
	Random rnd = new Random();
	
	Graph graph;
	Vertex[] data;
	
	public UI() {
		window.setBounds(50, 50, 500, 500);
		window.setAlwaysOnTop(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.setMinimumSize(new Dimension(600, 600));
		window.add(this);
		setLayout(new GridBagLayout());
		setLayout(new BorderLayout());
		
		// data for start and end panel
		try {
			graph = new Graph("MapInformation.txt");
			Set<Vertex> keys = graph.adjList.keySet();
			data = keys.toArray(new Vertex[keys.size()]);
			Arrays.sort(data);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		//============================================================ West Panel
		JPanel westJPanel = new JPanel();
		westJPanel.setLayout(new GridLayout(0, 1));
				
		// start list and it's scroll panel
		JList<Vertex> startJList = new JList<Vertex>(data);

		JScrollPane startListScrollPane = new JScrollPane();
		startListScrollPane.setViewportView(startJList);
		
		// end list and it's scroll panel
		JList<Vertex> endJList = new JList<Vertex>(data);

		JScrollPane endListScrollPane = new JScrollPane();
		endListScrollPane.setViewportView(endJList);
		
		// scroll panel container
		JPanel scrollPanelContainer = new JPanel();
		scrollPanelContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
		scrollPanelContainer.add(startListScrollPane);
		scrollPanelContainer.add(endListScrollPane);
		
		// path output
		JTextArea pathJTextArea = new JTextArea();
		
		// calculate shortest path btn
		JButton calcPathBtn = new JButton("Find Shortest Path");
		calcPathBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int startIndex = startJList.getSelectedIndex();
				int endIndex = endJList.getSelectedIndex();
				
				if (startIndex < 0 || endIndex < 0) {
					pathJTextArea.setText("Select both an end location and start location.");
					return;
				}
				
				Path p = Dijkstra.shortestPath(graph.adjList, data[startIndex].getSymbol(), data[endIndex].getSymbol());
				
				if (p == null)
					pathJTextArea.setText("No possible path");
				else {
					String output = (Graph.useDistCost ? "Distance" : "Time") + " Cost: " + p.getCost() + "\nPath: ";
					if (Graph.returnAddress) {
						String[] symbols = p.getPathString().split("-");
						for (String symbol : symbols)
							output += "\n  " + symbol + " - " + graph.addresses.get(symbol);
					} else
						output += p.getPathString();
					pathJTextArea.setText(output);
				}
			}
		});
		
		// labels for controls
		JLabel costJLabel = new JLabel("Cost:");
		JLabel avoidJLabel = new JLabel("Avoid:");
		
		// return addresses checkbox
		JCheckBox returnAddressesJCheckBox = new JCheckBox("return addresses");
		
		returnAddressesJCheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Graph.returnAddress = returnAddressesJCheckBox.isSelected();
			}
		});
		returnAddressesJCheckBox.setSelected(true);
		
		// return addresses container
		JPanel returnAddressesCheckBoxContainerJPanel = new JPanel();
		returnAddressesCheckBoxContainerJPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		returnAddressesCheckBoxContainerJPanel.add(returnAddressesJCheckBox);
		
		// cost radio buttons
		final String distJRadioButtonCommand = "use distance";
		final String timeJRadioButtonCommand = "use time";
		JRadioButton distJRadioButton = new JRadioButton(distJRadioButtonCommand);
		JRadioButton timeJRadioButton = new JRadioButton(timeJRadioButtonCommand);
		distJRadioButton.setSelected(true);
		
		ButtonGroup costButtonGroup = new ButtonGroup();
		costButtonGroup.add(distJRadioButton);
		costButtonGroup.add(timeJRadioButton);
		
		ActionListener costRadioButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Graph.useDistCost = e.getActionCommand().equals(distJRadioButtonCommand);
			}
		};
		
		distJRadioButton.addActionListener(costRadioButtonActionListener);
		timeJRadioButton.addActionListener(costRadioButtonActionListener);
		
		// cost radio button panel container
		JPanel costRadioButtonContainerJPanel = new JPanel();
		costRadioButtonContainerJPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		costRadioButtonContainerJPanel.add(costJLabel);
		costRadioButtonContainerJPanel.add(distJRadioButton);
		costRadioButtonContainerJPanel.add(timeJRadioButton);
		
		// avoid checkboxes
		JCheckBox avoidHighwayJCheckbox = new JCheckBox("Highways");
		JCheckBox avoidSpeedTrapsJCheckbox = new JCheckBox("Speed Traps");
		
		ItemListener avoidItemListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Object source = e.getSource();
				
				if (source == avoidHighwayJCheckbox) {
					Graph.avoidHighways = avoidHighwayJCheckbox.isSelected();
				} else if (source == avoidSpeedTrapsJCheckbox) {
					Graph.avoidSpeedTraps = avoidSpeedTrapsJCheckbox.isSelected();
				}
			}
		};
		
		avoidHighwayJCheckbox.addItemListener(avoidItemListener);
		avoidSpeedTrapsJCheckbox.addItemListener(avoidItemListener);
		
		// avoid checkbox panel container
		JPanel avoidCheckboxContainerJPanel = new JPanel();
		avoidCheckboxContainerJPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		avoidCheckboxContainerJPanel.add(avoidJLabel);
		avoidCheckboxContainerJPanel.add(avoidSpeedTrapsJCheckbox);
		avoidCheckboxContainerJPanel.add(avoidHighwayJCheckbox);
		
		// settings container
		JPanel settingsJPanel = new JPanel();
		settingsJPanel.setLayout(new GridLayout(0, 1));
		settingsJPanel.add(costRadioButtonContainerJPanel);
		settingsJPanel.add(avoidCheckboxContainerJPanel);
		settingsJPanel.add(returnAddressesCheckBoxContainerJPanel);
		
		// control container
		JPanel controlsJPanel = new JPanel();
		controlsJPanel.setLayout(new BorderLayout());
		controlsJPanel.add(settingsJPanel, BorderLayout.CENTER);
		controlsJPanel.add(calcPathBtn, BorderLayout.SOUTH);
		
		// add components to west panel
		westJPanel.add(scrollPanelContainer);
		westJPanel.add(controlsJPanel);
		westJPanel.add(pathJTextArea);
		
		//============================================================ Center Panel
		JPanel graphJPanel = new JPanel();
		BufferedImage image;
		try {
			image = ImageIO.read(new File("FinalProjectGraph_Final_400x400.png"));
			JLabel picLabel = new JLabel(new ImageIcon(image));
			graphJPanel.add(picLabel);
			graphJPanel.repaint();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//============================================================ North Panel
		JPanel northJPanel = new JPanel();
		northJPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		// file text field
		JTextField fileJTextField = new JTextField(20);
		fileJTextField.setText("MapInformation.txt");
		
		// read file button
		JButton readFileJButton = new JButton("read file");
		readFileJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File f = new File(fileJTextField.getText());
				if(f.exists() && !f.isDirectory()) { 
					try {
						graph = new Graph(fileJTextField.getText());
						Set<Vertex> keys = graph.adjList.keySet();
						data = keys.toArray(new Vertex[keys.size()]);
						Arrays.sort(data);
						
						startJList.setListData(data);
						endJList.setListData(data);
						pathJTextArea.setText("File Read Successful");
					} catch (Exception err) {
						pathJTextArea.setText("Incorrect File Format");
					}
				} else
					pathJTextArea.setText("File Does Not Exist");
				
			}
		});
		
		// add components to northJPanel
		northJPanel.add(fileJTextField);
		northJPanel.add(readFileJButton);
		
		//============================================================ add all components to window
		add(northJPanel, BorderLayout.NORTH);
		add(westJPanel, BorderLayout.WEST);
		add(graphJPanel, BorderLayout.CENTER);
		
		window.setVisible(true);
//============================================================ Events
		tmr = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
//============================================================ Mouse Pressed
		addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
//============================================================ Mouse Moved, Dragged
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				mouseMoved(e);
			}
		});
//============================================================ Key Pressed
		window.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
			}
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		tmr.start();
	}
//============================================================ Drawing
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
//======================================================
	public static void main(String[] args) { new UI(); }
//======================================================
}