package com.jrcesar4.batchimportjson;

import com.jrcesar4.batchimportjson.dto.Root;
import com.jrcesar4.batchimportjson.entity.EmpresaEntity;
import com.jrcesar4.batchimportjson.service.JsonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
@EntityScan(basePackageClasses = { EmpresaEntity.class})
@Slf4j
public class BatchImportJsonApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BatchImportJsonApplication.class, args);
	}


	@Autowired
	private JsonService jsonService;

	@Override
	public void run(String... args) throws Exception {
		log.info("Starting....");

		String files = readArg(args);
		Files.list(Paths.get(files))
				.forEach(path -> {
					String json = jsonService.readJsonFile(path.toString());
					log.info(json);
					Root root = jsonService.parseJson(json);
					jsonService.saveData(root);
				});


		log.info("Finish Success....  \\o/");
		if (true) System.exit(0);
	}

	private String readArg(String[] args) {
		String value = new String();
		try{
			value = args[0];
		}catch (Exception e){
			log.error(e.getMessage());
		}
		return value;
	}
}
