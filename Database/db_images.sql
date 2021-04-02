-- phpMyAdmin SQL Dump
-- version 4.9.5deb2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Mar 25, 2021 at 04:01 PM
-- Server version: 8.0.23-0ubuntu0.20.04.1
-- PHP Version: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_images`
--

-- --------------------------------------------------------

--
-- Table structure for table `001`
--

CREATE TABLE `001` (
  `Timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Mental_State` text NOT NULL,
  `Visualization_State` text NOT NULL,
  `alert` int DEFAULT NULL,
  `alert_count` int NOT NULL,
  `alert_acted_count` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `160101071`
--

CREATE TABLE `160101071` (
  `Time` datetime NOT NULL,
  `Mental_State` text NOT NULL,
  `Derived_State` text NOT NULL,
  `alert` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `attendance_records`
--

CREATE TABLE `attendance_records` (
  `username` varchar(20) NOT NULL,
  `attendance_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `class` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `attendance_records`
--

INSERT INTO `attendance_records` (`username`, `attendance_time`, `class`) VALUES
('htc', '2021-03-04 16:52:16', 'CS');

-- --------------------------------------------------------

--
-- Table structure for table `attendance_status`
--

CREATE TABLE `attendance_status` (
  `status` varchar(8) NOT NULL DEFAULT 'false',
  `class` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `attendance_status`
--

INSERT INTO `attendance_status` (`status`, `class`) VALUES
('false', 'CS');

-- --------------------------------------------------------

--
-- Table structure for table `images`
--

CREATE TABLE `images` (
  `id` varchar(255) NOT NULL,
  `url` text NOT NULL,
  `name` text NOT NULL,
  `roll` varchar(50) NOT NULL,
  `pass` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `images`
--

INSERT INTO `images` (`id`, `url`, `name`, `roll`, `pass`) VALUES
('001', 'http://localhost/AndroidImageUpload/uploads/001.jpg', 'animesh', '001', 'animesh'),
('160101071', 'http://localhost/AndroidImageUpload/uploads/160101071.jpeg', 'unable error', '160101071', '123'),
('160101072', 'http://localhost/AndroidImageUpload/uploads/160101072.jpeg', '160101072', '160101072', '123456'),
('asus', 'http://localhost/AndroidImageUpload/uploads/asus.jpeg', 'asus', 'asus', '123456'),
('htc', 'http://localhost/AndroidImageUpload/uploads/htc.jpeg', 'htc', 'htc', '123456'),
('IITG100', '', 'sid', 'IITG100', '100'),
('moto', 'http://localhost/AndroidImageUpload/uploads/moto.jpeg', 'moto', 'moto', '123456'),
('samsung', 'http://localhost/AndroidImageUpload/uploads/samsung.jpeg', 'samsung', 'samsung', '123456'),
('sony1', 'http://localhost/AndroidImageUpload/uploads/sony1.jpeg', 'sony1', 'sony1', '123456'),
('sony2', 'http://localhost/AndroidImageUpload/uploads/sony2.jpeg', 'sony2', 'sony2', '123456');

-- --------------------------------------------------------

--
-- Table structure for table `image_tbl`
--

CREATE TABLE `image_tbl` (
  `id` int NOT NULL,
  `roll` text NOT NULL,
  `image_url` text NOT NULL,
  `PosX` int NOT NULL,
  `PosY` int NOT NULL,
  `State_in` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `image_tbl`
--

INSERT INTO `image_tbl` (`id`, `roll`, `image_url`, `PosX`, `PosY`, `State_in`) VALUES
(1, 'empty', 'http://localhost/AndroidImageUpload/uploads/htc.jpeg', 1, 1, 'DS0'),
(2, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 1, 2, 'DS0'),
(3, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 1, 3, 'DS0'),
(4, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 1, 4, 'DS0'),
(5, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 1, 5, 'DS0'),
(6, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 1, 6, 'DS0'),
(7, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 1, 'DS0'),
(8, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 2, 'DS0'),
(9, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 3, 'DS0'),
(10, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 4, 'DS0'),
(11, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 5, 'DS0'),
(12, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 6, 'DS0'),
(13, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 1, 'DS0'),
(14, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 2, 'DS0'),
(15, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 3, 'DS0'),
(16, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 4, 'DS0'),
(17, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 5, 'DS0'),
(18, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 6, 'DS0'),
(19, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 1, 'DS0'),
(20, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 2, 'DS0'),
(21, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 3, 'DS0'),
(22, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 4, 'DS0'),
(23, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 5, 'DS0'),
(24, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 6, 'DS0'),
(25, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 1, 'DS0'),
(26, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 2, 'DS0'),
(27, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 3, 'DS0'),
(28, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 4, 'DS0'),
(29, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 5, 'DS0'),
(30, 'empty', 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 6, 'DS0');

-- --------------------------------------------------------

--
-- Table structure for table `queries`
--

CREATE TABLE `queries` (
  `q_id` int NOT NULL,
  `query` text NOT NULL,
  `page` int NOT NULL,
  `slide` text NOT NULL,
  `likes` int NOT NULL DEFAULT '0',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_answered` int NOT NULL DEFAULT '0',
  `answer` text,
  `answer_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Seat_arrangement`
--

CREATE TABLE `Seat_arrangement` (
  `student_id` varchar(10) NOT NULL,
  `seat_row` int NOT NULL,
  `seat_column` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Seat_arrangement`
--

INSERT INTO `Seat_arrangement` (`student_id`, `seat_row`, `seat_column`) VALUES
('001', 1, 1),
('160101071', 1, 2),
('asus', 1, 3),
('htc', 2, 1),
('moto', 2, 2),
('samsung', 2, 3),
('sony2', 3, 1),
('sony1', 3, 2),
('160101072', 3, 3);

-- --------------------------------------------------------

--
-- Table structure for table `slides`
--

CREATE TABLE `slides` (
  `id` int NOT NULL,
  `path` text NOT NULL,
  `name` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `slides`
--

INSERT INTO `slides` (`id`, `path`, `name`) VALUES
(16, 'http://localhost/Sites/Server/CoreFunctionality/uploads/siddharth.pdf', 'siddharth.pdf'),
(17, 'http://localhost/Sites/Server/CoreFunctionality/uploads/networks.pdf', 'networks.pdf'),
(18, 'http://localhost/Sites/Server/CoreFunctionality/uploads/stackoverflow.pdf', 'stackoverflow.pdf'),
(19, 'http://localhost/Sites/Server/CoreFunctionality/uploads/Test 1.pdf', 'Test 1.pdf'),
(20, 'http://localhost/Sites/Server/CoreFunctionality/uploads/y.pdf', 'y.pdf'),
(21, 'http://localhost/Sites/Server/CoreFunctionality/uploads/79427439_POLICY_DOC aryaman.pdf', '79427439_POLICY_DOC aryaman.pdf'),
(22, 'http://localhost/Sites/Server/CoreFunctionality/uploads/3ca41add-0ed6-4594-af48-87c4b1b5e17e.pdf', '3ca41add-0ed6-4594-af48-87c4b1b5e17e.pdf'),
(23, 'http://localhost/Sites/Server/CoreFunctionality/uploads/1e725cf5-6c68-40c5-8d0b-a33b023904d2.pdf', '1e725cf5-6c68-40c5-8d0b-a33b023904d2.pdf'),
(24, 'http://localhost/Sites/Server/CoreFunctionality/uploads/0f8ef66c-57c3-4b14-87be-ee50fc484a4c.pdf', '0f8ef66c-57c3-4b14-87be-ee50fc484a4c.pdf'),
(25, 'http://localhost/Sites/Server/CoreFunctionality/uploads/0f8ef66c-57c3-4b14-87be-ee50fc484a4c.pdf', '0f8ef66c-57c3-4b14-87be-ee50fc484a4c.pdf'),
(26, 'http://localhost/Sites/Server/CoreFunctionality/uploads/0f8ef66c-57c3-4b14-87be-ee50fc484a4c.pdf', '0f8ef66c-57c3-4b14-87be-ee50fc484a4c.pdf'),
(27, 'http://localhost/Sites/Server/CoreFunctionality/uploads/Ar.pdf', 'Ar.pdf'),
(28, 'http://localhost/Sites/Server/CoreFunctionality/uploads/30013402445815.pdf', '30013402445815.pdf'),
(29, 'http://localhost/Sites/Server/CoreFunctionality/uploads/30013402445815.pdf', '30013402445815.pdf');

-- --------------------------------------------------------

--
-- Table structure for table `State_Calc`
--

CREATE TABLE `State_Calc` (
  `timestamp` datetime NOT NULL,
  `roll` varchar(50) NOT NULL,
  `involvement` int NOT NULL,
  `Arousal` int NOT NULL,
  `Valence` int NOT NULL,
  `Arousal_Valence_state` int NOT NULL,
  `Classroom_Activity_State` int NOT NULL,
  `Mental_State` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `State_Calc`
--

INSERT INTO `State_Calc` (`timestamp`, `roll`, `involvement`, `Arousal`, `Valence`, `Arousal_Valence_state`, `Classroom_Activity_State`, `Mental_State`) VALUES
('2018-07-06 23:18:55', 'siddhi', 1, 1, 1, 1, 1, 's1');

-- --------------------------------------------------------

--
-- Table structure for table `teacher`
--

CREATE TABLE `teacher` (
  `Timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `AlertType` int NOT NULL,
  `Alert_count_not_engaged` int NOT NULL,
  `Alert_count_not_understanding` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `temp_states`
--

CREATE TABLE `temp_states` (
  `student_id` varchar(10) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Involvement` int NOT NULL,
  `Activity` int NOT NULL,
  `Arousal` int NOT NULL,
  `Valence` int NOT NULL,
  `State` int NOT NULL,
  `Mental_State` int NOT NULL,
  `Visualization_State` int NOT NULL,
  `Alert` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `temp_states`
--

INSERT INTO `temp_states` (`student_id`, `Involvement`, `Activity`, `Arousal`, `Valence`, `State`, `Mental_State`, `Visualization_State`, `Alert`) VALUES
('001', 1, 1, 1, 1, 1, 1, 2, 0),
('160101071', 1, 1, 1, 1, 1, 1, 3, 1),
('asus', 1, 1, 1, 1, 1, 1, 3, 1),
('htc', 1, 1, 1, 1, 1, 1, 3, 1),
('moto', 1, 1, 1, 1, 1, 1, 3, 1),
('sony1', 1, 1, 1, 1, 1, 1, 3, 1),
('sony2', 1, 1, 1, 1, 1, 1, 3, 1),
('samsung', 1, 1, 1, 1, 1, 1, 3, 1),
('160101072', 1, 1, 1, 1, 1, 1, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('sony1', 1, 1, 0, 0, 0, 5, 1, 1),
('sony1', 1, 1, 0, 0, 0, 5, 1, 1),
('sony1', 1, 1, 0, 0, 0, 5, 1, 1),
('sony1', 1, 1, 0, 0, 0, 5, 1, 1),
('sony1', 1, 1, 0, 0, 0, 5, 1, 1),
('sony1', 1, 1, 0, 0, 0, 5, 1, 1),
('sony1', 1, 1, 0, 0, 0, 5, 1, 1),
('sony1', 1, 1, 0, 0, 0, 5, 1, 1),
('sony1', 1, 1, 0, 0, 0, 5, 1, 1),
('sony1', 1, 1, 0, 0, 0, 5, 1, 1),
('sony1', 1, 1, 0, 0, 0, 5, 1, 1),
('sony1', 1, 1, 0, 0, 0, 5, 1, 1),
('sony1', 1, 1, 0, 0, 0, 5, 1, 1),
('sony1', 1, 1, 0, 0, 0, 5, 1, 1),
('sony1', 1, 1, 0, 0, 0, 5, 1, 1),
('sony1', 1, 1, 0, 0, 0, 5, 1, 1),
('sony1', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('null', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', 1, 1, 0, 0, 0, 5, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `Visualisation_States`
--

CREATE TABLE `Visualisation_States` (
  `timestamp` datetime NOT NULL,
  `roll` text NOT NULL,
  `Derived_States` int NOT NULL,
  `Alert` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `images`
--
ALTER TABLE `images`
  ADD PRIMARY KEY (`roll`);

--
-- Indexes for table `image_tbl`
--
ALTER TABLE `image_tbl`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `queries`
--
ALTER TABLE `queries`
  ADD PRIMARY KEY (`q_id`);

--
-- Indexes for table `slides`
--
ALTER TABLE `slides`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `State_Calc`
--
ALTER TABLE `State_Calc`
  ADD PRIMARY KEY (`roll`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `image_tbl`
--
ALTER TABLE `image_tbl`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `queries`
--
ALTER TABLE `queries`
  MODIFY `q_id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `slides`
--
ALTER TABLE `slides`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
