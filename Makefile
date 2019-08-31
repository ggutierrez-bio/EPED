SRC_DIR		:= $(PWD)/src
PKG		:= es/uned/lsi/eped/pract2018_2019
MAIN		:= $(PKG)/Main
JAVA_HOME_JDK	:= /usr/lib/jvm/java-8-openjdk-amd64
TMP_FOLDER	:= $(PWD)/juego_de_pruebas/tmp
BIN_DIR		:= $(TMP_FOLDER)/bin

.PHONY: clean build prepare test

build: clean
	$(MAKE) prepare
	$(JAVA_HOME_JDK)/bin/javac -d $(BIN_DIR) -sourcepath $(TMP_FOLDER)/src -cp "juego_de_pruebas/lib/TAD_modified.jar" $(TMP_FOLDER)/src/$(MAIN).java

prepare:
	@mkdir -p $(TMP_FOLDER)/src/$(PKG) $(BIN_DIR)
	@cp -r $(SRC_DIR)/$(PKG)/* $(TMP_FOLDER)/src/$(PKG)

clean:
	@rm -rf $(TMP_FOLDER)

test: build
	$(JAVA_HOME_JDK)/bin/java -cp $(BIN_DIR):juego_de_pruebas/lib/TAD_modified.jar $(MAIN) SEQ "juego_de_pruebas/pruebas/Estudiantes_SEQ.txt" > "juego_de_pruebas/salida/Salida_SEQ.txt"
	$(JAVA_HOME_JDK)/bin/java -jar juego_de_pruebas/lib/Comparator.jar juego_de_pruebas/salida/Salida_SEQ.txt juego_de_pruebas/salida/SalidaEsperada_SEQ.txt
