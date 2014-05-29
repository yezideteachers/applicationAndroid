-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Client: 127.0.0.1
-- Généré le: Lun 26 Mai 2014 à 00:34
-- Version du serveur: 5.5.27-log
-- Version de PHP: 5.4.6

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `rally`
--

-- --------------------------------------------------------

--
-- Structure de la table `joueur`
--

CREATE TABLE IF NOT EXISTS `joueur` (
  `Pseudo` varchar(20) NOT NULL,
  `MotDePasse` varchar(20) NOT NULL,
  `Score` bigint(20) NOT NULL,
  PRIMARY KEY (`Pseudo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `joueur`
--

INSERT INTO `joueur` (`Pseudo`, `MotDePasse`, `Score`) VALUES
('joueur1', 'pass', 0),
('joueur2', 'pass', 0),
('joueur3', 'pass', 0),
('joueur4', 'pass', 0);

-- --------------------------------------------------------

--
-- Structure de la table `parcours`
--

CREATE TABLE IF NOT EXISTS `parcours` (
  `Nom` varchar(20) NOT NULL,
  `LogitudeDepart` double NOT NULL,
  `LatitudeDepart` double NOT NULL,
  `LongitudeArrivée` double NOT NULL,
  `LatitudeArrivée` double NOT NULL,
  PRIMARY KEY (`Nom`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `question`
--

CREATE TABLE IF NOT EXISTS `question` (
  `Question` varchar(100) NOT NULL,
  `Reponse` varchar(50) NOT NULL,
  `Choix1` varchar(50) NOT NULL,
  `Choix2` varchar(50) NOT NULL,
  PRIMARY KEY (`Question`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `question`
--

INSERT INTO `question` (`Question`, `Reponse`, `Choix1`, `Choix2`) VALUES
('le chiffre 21 est representé en binaire par 1101', 'non', 'oui', 'non'),
('Le GC existe dans le langage C', 'non', 'oui', 'non');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
