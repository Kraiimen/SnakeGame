package carmineerario.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GameConfig {
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	private static final String RECORDS_FILE = getAppDataPath() + File.separator + "records.json";
	private static final String CONFIG_FILE = getAppDataPath() + File.separator + "config.json";

	private List<Player> records;

	private static String getAppDataPath() {
		String os = System.getProperty("os.name").toLowerCase();
		String appDataPath;

		if (os.contains("win")) {
			// Windows
			appDataPath = System.getenv("APPDATA") + File.separator + "SnakeGame";
		} else {
			// Linux/MacOS
			appDataPath = System.getProperty("user.home") + File.separator + ".SnakeGame";
		}

		Path path = Paths.get(appDataPath);
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return appDataPath;
	}

	static class Config{
		int gameDifficulty = 80;
		int[] snakeColorArr = new int[3];
	}

	public record Player(String playerName, int playerRecord) {}

	public static void saveConfig(int gameDifficulty, int[] snakeColorArr) {
		Config config = new Config();
		config.gameDifficulty = gameDifficulty;
		config.snakeColorArr = snakeColorArr;
		
		try (FileWriter writer = new FileWriter(CONFIG_FILE)){
			gson.toJson(config, writer);
		} catch (IOException e) {
			System.out.println("saveConfig() error");
		}
	}

	static void saveRecordsToFile(List<Player> records) {
		try (FileWriter writer = new FileWriter(RECORDS_FILE)){
			gson.toJson(records, writer);
		} catch (IOException e) {
			System.out.println("saveRecordsToFile() error");
		}
	}

	public static void saveNewRecord(Player player) {
		List<Player> tempRecords = loadRecords();
		int newRecordToSave = player.playerRecord;

		int insertPos = 0;
		while (insertPos < tempRecords.size() && tempRecords.get(insertPos).playerRecord >= newRecordToSave){
			insertPos++;
		}

		tempRecords.add(insertPos, player);

		if (tempRecords.size() > 5){
			tempRecords.removeLast();
		}

		saveRecordsToFile(tempRecords);
	}

	public static boolean canRecordBeSaved(int playerRecord) {
		List<Player> tempRecords = loadRecords();

		if(!(playerRecord == tempRecords.getLast().playerRecord())){
            for (Player player : tempRecords) {
                if (playerRecord >= player.playerRecord()) {
                    return true;
                }
            }
		}

		return false;
	}

	public static int loadDifficulty() {
		checkIfConfigFileExists();

        try (FileReader reader = new FileReader(CONFIG_FILE)) {
			return gson.fromJson(reader, Config.class).gameDifficulty;
        } catch (IOException e) {
			System.out.println("loadDifficulty() error");
            return 80;
        }
	}

	public static int[] loadSnakeColor() {
		checkIfConfigFileExists();

		try (FileReader reader = new FileReader(CONFIG_FILE)) {
			return gson.fromJson(reader, Config.class).snakeColorArr;
		} catch (IOException e) {
			System.out.println("loadSnakeColor() error");
			return new int[] {0,0,0};
		}
	}

	public static List<Player> loadRecords(){
		checkIfRecordsFileExists();

		try (FileReader reader = new FileReader(RECORDS_FILE)) {
			return gson.fromJson(reader, new TypeToken<List<Player>>(){}.getType());
		} catch (IOException e) {
			System.out.println("loadRecords() error");
			return List.of();
		}
	}

	private static void checkIfConfigFileExists(){
		File file = new File(CONFIG_FILE);
		if(!file.exists()){
			createDefaultConfigFile(file);
		}
	}

	private static void checkIfRecordsFileExists(){
		File file = new File(RECORDS_FILE);
		if(!file.exists()){
			createDefaultRecordsFile(file);
		}
	}

	private static void createDefaultConfigFile(File file) {
		Config defaultConfig = new Config();

		try (FileWriter writer = new FileWriter(file)) {
			gson.toJson(defaultConfig, writer);
		} catch (IOException e) {
			System.out.println("Default config file not created.");
		}
	}

	private static void createDefaultRecordsFile(File file) {
		List<Player> defaultRecords = new ArrayList<>();
		defaultRecords.add(new Player("Player1", 0));
		defaultRecords.add(new Player("Player2", 0));
		defaultRecords.add(new Player("Player3", 0));
		defaultRecords.add(new Player("Player4", 0));
		defaultRecords.add(new Player("Player5", 0));

		try (FileWriter writer = new FileWriter(file)) {
			gson.toJson(defaultRecords, writer);
		} catch (IOException e) {
			System.out.println("Default records file not created.");
		}
	}
}