-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Feb 09, 2019 alle 17:05
-- Versione del server: 10.1.36-MariaDB
-- Versione PHP: 7.2.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mydb`
--
CREATE DATABASE IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `mydb`;

-- --------------------------------------------------------

--
-- Struttura della tabella `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `idAdmin` int(1) NOT NULL,
  `email` varchar(60) DEFAULT NULL,
  `nome` varchar(45) DEFAULT NULL,
  `cognome` varchar(45) DEFAULT NULL,
  `users_idUsers` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `admin`
--

INSERT INTO `admin` (`idAdmin`, `email`, `nome`, `cognome`, `users_idUsers`) VALUES
(1, 'gaetano.panarosa@alice.it', 'admin', 'admin', 2);

-- --------------------------------------------------------

--
-- Struttura della tabella `escursione`
--

DROP TABLE IF EXISTS `escursione`;
CREATE TABLE `escursione` (
  `idEscursione` int(1) NOT NULL,
  `codiceEscursione` varchar(45) DEFAULT NULL,
  `dataEscursione` varchar(45) DEFAULT NULL,
  `maxPartecipanti` int(20) DEFAULT NULL,
  `minPartecipanti` int(20) DEFAULT NULL,
  `prezzo` double NOT NULL,
  `postiDisponibili` int(11) DEFAULT NULL,
  `admin` int(1) NOT NULL,
  `tipoescursione_idtipoEscursione` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `escursione`
--

INSERT INTO `escursione` (`idEscursione`, `codiceEscursione`, `dataEscursione`, `maxPartecipanti`, `minPartecipanti`, `prezzo`, `postiDisponibili`, `admin`, `tipoescursione_idtipoEscursione`) VALUES
(3, '001', '2019-02-14', 100, 50, 50, 100, 1, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `escursione_optional`
--

DROP TABLE IF EXISTS `escursione_optional`;
CREATE TABLE `escursione_optional` (
  `idEscursione_optional` int(1) NOT NULL,
  `escursione` int(1) NOT NULL,
  `optional` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dump dei dati per la tabella `escursione_optional`
--

INSERT INTO `escursione_optional` (`idEscursione_optional`, `escursione`, `optional`) VALUES
(127, 3, 1),
(128, 3, 4);

-- --------------------------------------------------------

--
-- Struttura della tabella `escursione_stato`
--

DROP TABLE IF EXISTS `escursione_stato`;
CREATE TABLE `escursione_stato` (
  `idEscursione_stato` int(11) NOT NULL,
  `escursione` int(11) DEFAULT NULL,
  `stato` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dump dei dati per la tabella `escursione_stato`
--

INSERT INTO `escursione_stato` (`idEscursione_stato`, `escursione`, `stato`) VALUES
(108, 3, 2);

-- --------------------------------------------------------

--
-- Struttura della tabella `optional`
--

DROP TABLE IF EXISTS `optional`;
CREATE TABLE `optional` (
  `idOptional` int(1) NOT NULL AUTO_INCREMENT,
  `nomeOptional` varchar(45) NOT NULL,
  `prezzoOptional` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `optional`
--

INSERT INTO `optional` (`idOptional`, `nomeOptional`, `prezzoOptional`) VALUES
(1, 'Pranzo', 40),
(2, 'Merenda', 10),
(3, 'Visita', 50),
(4, 'Nessun Optional', 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `partecipazioni`
--

DROP TABLE IF EXISTS `partecipazioni`;
CREATE TABLE `partecipazioni` (
  `idPartecipazioni` int(1) NOT NULL,
  `escursione` int(1) DEFAULT NULL,
  `utente` int(1) DEFAULT NULL,
  `prezzoPartecipazione` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dump dei dati per la tabella `partecipazioni`
--

INSERT INTO `partecipazioni` (`idPartecipazioni`, `escursione`, `utente`, `prezzoPartecipazione`) VALUES
(15, 3, 1, 90);

-- --------------------------------------------------------

--
-- Struttura della tabella `partecipazioni_optional`
--

DROP TABLE IF EXISTS `partecipazioni_optional`;
CREATE TABLE `partecipazioni_optional` (
  `idPartecipazioni_optional` int(1) NOT NULL,
  `partecipazione` int(1) NOT NULL,
  `optional` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dump dei dati per la tabella `partecipazioni_optional`
--

INSERT INTO `partecipazioni_optional` (`idPartecipazioni_optional`, `partecipazione`, `optional`) VALUES
(1, 15, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `stato`
--

DROP TABLE IF EXISTS `stato`;
CREATE TABLE `stato` (
  `idStato` int(11) NOT NULL,
  `stato` varchar(45) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dump dei dati per la tabella `stato`
--

INSERT INTO `stato` (`idStato`, `stato`) VALUES
(1, 'Non attiva'),
(2, 'Attiva'),
(3, 'In Corso');

-- --------------------------------------------------------

--
-- Struttura della tabella `tipoescursione`
--

DROP TABLE IF EXISTS `tipoescursione`;
CREATE TABLE `tipoescursione` (
  `idtipoEscursione` int(1) NOT NULL,
  `tipo` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `tipoescursione`
--

INSERT INTO `tipoescursione` (`idtipoEscursione`, `tipo`) VALUES
(2, 'Gita a Cavallo'),
(1, 'Gita in Barca');

-- --------------------------------------------------------

--
-- Struttura della tabella `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `idUsers` int(1) NOT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `tipo` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `users`
--

INSERT INTO `users` (`idUsers`, `username`, `password`, `tipo`) VALUES
(1, 'gaetano92', 'acerdata', 1),
(2, 'root', 'root', 2);

-- --------------------------------------------------------

--
-- Struttura della tabella `utente`
--

DROP TABLE IF EXISTS `utente`;
CREATE TABLE `utente` (
  `idUtente` int(1) NOT NULL,
  `nome` varchar(45) DEFAULT NULL,
  `cognome` varchar(45) DEFAULT NULL,
  `codiceFiscale` varchar(45) NOT NULL,
  `dataNascita` date DEFAULT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `indirizzo` varchar(45) DEFAULT NULL,
  `sesso` varchar(45) DEFAULT NULL,
  `città` varchar(45) DEFAULT NULL,
  `paese` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `users_idUsers` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `utente`
--

INSERT INTO `utente` (`idUtente`, `nome`, `cognome`, `codiceFiscale`, `dataNascita`, `telefono`, `indirizzo`, `sesso`, `città`, `paese`, `email`, `users_idUsers`) VALUES
(1, 'Gaetano', 'Panarosa', 'PNRGTN92D14F280M', '1992-04-14', '3427749283', 'Via Ugo Foscolo 148', 'M', 'Mola di Bari', 'Italia', 'gaetano.panarosa@alice.it', 1);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`idAdmin`),
  ADD UNIQUE KEY `email_UNIQUE` (`email`),
  ADD KEY `fk_admin_users1_idx` (`users_idUsers`);

--
-- Indici per le tabelle `escursione`
--
ALTER TABLE `escursione`
  ADD PRIMARY KEY (`idEscursione`,`admin`,`tipoescursione_idtipoEscursione`),
  ADD UNIQUE KEY `codiceEscursione_UNIQUE` (`codiceEscursione`),
  ADD KEY `fk_escursione_tipoescursione1_idx` (`tipoescursione_idtipoEscursione`);

--
-- Indici per le tabelle `escursione_optional`
--
ALTER TABLE `escursione_optional`
  ADD PRIMARY KEY (`idEscursione_optional`),
  ADD KEY `fk_es_opt_escursione` (`escursione`);

--
-- Indici per le tabelle `escursione_stato`
--
ALTER TABLE `escursione_stato`
  ADD PRIMARY KEY (`idEscursione_stato`),
  ADD KEY `fk_escursione_stato_escursione_idx` (`escursione`);

--
-- Indici per le tabelle `optional`
--
ALTER TABLE `optional`
  ADD PRIMARY KEY (`idOptional`),
  ADD UNIQUE KEY `prezzoOptional_UNIQUE` (`prezzoOptional`),
  ADD KEY `nomeOptional_idx` (`nomeOptional`),
  ADD KEY `idOptional` (`idOptional`);

--
-- Indici per le tabelle `partecipazioni`
--
ALTER TABLE `partecipazioni`
  ADD PRIMARY KEY (`idPartecipazioni`),
  ADD KEY `fk_partecipazioni_escursione` (`escursione`);

--
-- Indici per le tabelle `partecipazioni_optional`
--
ALTER TABLE `partecipazioni_optional`
  ADD PRIMARY KEY (`idPartecipazioni_optional`),
  ADD KEY `fk_partecipazioni_optional_optional` (`optional`),
  ADD KEY `fk_partecipazioni_optional_partecipazione` (`partecipazione`);

--
-- Indici per le tabelle `stato`
--
ALTER TABLE `stato`
  ADD PRIMARY KEY (`idStato`);

--
-- Indici per le tabelle `tipoescursione`
--
ALTER TABLE `tipoescursione`
  ADD PRIMARY KEY (`idtipoEscursione`),
  ADD UNIQUE KEY `tipo_UNIQUE` (`tipo`);

--
-- Indici per le tabelle `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`idUsers`),
  ADD UNIQUE KEY `username_UNIQUE` (`username`);

--
-- Indici per le tabelle `utente`
--
ALTER TABLE `utente`
  ADD PRIMARY KEY (`idUtente`),
  ADD UNIQUE KEY `codiceFiscale_UNIQUE` (`codiceFiscale`),
  ADD UNIQUE KEY `email_UNIQUE` (`email`),
  ADD KEY `fk_utente_users1_idx` (`users_idUsers`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `admin`
--
ALTER TABLE `admin`
  MODIFY `idAdmin` int(1) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT per la tabella `escursione`
--
ALTER TABLE `escursione`
  MODIFY `idEscursione` int(1) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `escursione_optional`
--
ALTER TABLE `escursione_optional`
  MODIFY `idEscursione_optional` int(1) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=129;

--
-- AUTO_INCREMENT per la tabella `escursione_stato`
--
ALTER TABLE `escursione_stato`
  MODIFY `idEscursione_stato` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=109;

--
-- AUTO_INCREMENT per la tabella `partecipazioni`
--
ALTER TABLE `partecipazioni`
  MODIFY `idPartecipazioni` int(1) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT per la tabella `partecipazioni_optional`
--
ALTER TABLE `partecipazioni_optional`
  MODIFY `idPartecipazioni_optional` int(1) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT per la tabella `stato`
--
ALTER TABLE `stato`
  MODIFY `idStato` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `users`
--
ALTER TABLE `users`
  MODIFY `idUsers` int(1) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `utente`
--
ALTER TABLE `utente`
  MODIFY `idUtente` int(1) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `fk_admin_users1` FOREIGN KEY (`users_idUsers`) REFERENCES `users` (`idUsers`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `escursione`
--
ALTER TABLE `escursione`
  ADD CONSTRAINT `fk_escursione_tipoescursione1` FOREIGN KEY (`tipoescursione_idtipoEscursione`) REFERENCES `tipoescursione` (`idtipoEscursione`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `escursione_optional`
--
ALTER TABLE `escursione_optional`
  ADD CONSTRAINT `fk_es_opt_escursione` FOREIGN KEY (`escursione`) REFERENCES `escursione` (`idEscursione`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_es_opt_optional` FOREIGN KEY (`optional`) REFERENCES `optional` (`idOptional`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `partecipazioni`
--
ALTER TABLE `partecipazioni`
  ADD CONSTRAINT `fk_partecipazioni_escursione` FOREIGN KEY (`escursione`) REFERENCES `escursione` (`idEscursione`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `partecipazioni_optional`
--
ALTER TABLE `partecipazioni_optional`
  ADD CONSTRAINT `fk_partecipazioni_optional_optional` FOREIGN KEY (`optional`) REFERENCES `escursione_optional` (`optional`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_partecipazioni_optional_partecipazione` FOREIGN KEY (`partecipazione`) REFERENCES `partecipazioni` (`idPartecipazioni`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `utente`
--
ALTER TABLE `utente`
  ADD CONSTRAINT `fk_utente_users1` FOREIGN KEY (`users_idUsers`) REFERENCES `users` (`idUsers`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
