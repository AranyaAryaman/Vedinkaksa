-- phpMyAdmin SQL Dump
-- version 4.9.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jun 11, 2020 at 08:00 PM
-- Server version: 10.3.8-MariaDB
-- PHP Version: 7.3.11

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
  `Timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `Mental_State` text NOT NULL,
  `Visualization_State` text NOT NULL,
  `alert` int(11) DEFAULT NULL,
  `alert_count` int(11) NOT NULL,
  `alert_acted_count` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `160101071`
--

CREATE TABLE `160101071` (
  `Time` datetime NOT NULL,
  `Mental_State` text NOT NULL,
  `Derived_State` text NOT NULL,
  `alert` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
  `id` int(11) NOT NULL,
  `roll` text NOT NULL,
  `image_url` text NOT NULL,
  `PosX` int(11) NOT NULL,
  `PosY` int(11) NOT NULL,
  `State_in` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `queries`
--

CREATE TABLE `queries` (
  `q_id` int(11) NOT NULL,
  `query` text NOT NULL,
  `page` int(11) NOT NULL,
  `slide` text NOT NULL,
  `likes` int(11) NOT NULL DEFAULT 0,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp(),
  `is_answered` int(11) NOT NULL DEFAULT 0,
  `answer` text DEFAULT NULL,
  `answer_added` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `queries`
--

INSERT INTO `queries` (`q_id`, `query`, `page`, `slide`, `likes`, `timestamp`, `is_answered`, `answer`, `answer_added`) VALUES
(2, 'what is abc', 4, 'networks.pdf', 3, '2019-09-12 11:28:23', 0, NULL, '2019-09-12 11:28:23'),
(18, 'how are you?', 0, 'networks.pdf', 3, '2019-11-16 05:53:27', 1, 'good', '2019-11-16 06:03:45'),
(19, 'hello', 4, 'networks.pdf', 0, '2019-11-16 06:11:08', 1, 'xffh', '2019-11-16 06:11:20'),
(20, 'question 1', 2, 'networks.pdf', 0, '2019-11-16 06:26:51', 0, NULL, '2019-11-16 06:26:51'),
(21, 'sample', 1, 'networks.pdf', 0, '2019-11-16 06:29:25', 1, 'answer', '2019-11-16 06:29:53'),
(22, 'ques', 0, 'networks.pdf', 5, '2019-11-16 06:31:48', 1, 'ans', '2019-11-16 06:32:10'),
(23, 'test question', 3, 'networks.pdf', 1, '2020-02-14 12:05:44', 0, NULL, '2020-02-14 12:05:44');

-- --------------------------------------------------------

--
-- Table structure for table `Seat_arrangement`
--

CREATE TABLE `Seat_arrangement` (
  `student_id` varchar(10) NOT NULL,
  `seat_row` int(11) NOT NULL,
  `seat_column` int(11) NOT NULL
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
  `id` int(11) NOT NULL,
  `path` text NOT NULL,
  `name` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `slides`
--

INSERT INTO `slides` (`id`, `path`, `name`) VALUES
(16, 'http://localhost/Sites/Server/AndroidImageUpload/uploads/siddharth.pdf', 'siddharth.pdf'),
(17, 'http://localhost/Sites/Server/AndroidImageUpload/uploads/networks.pdf', 'networks.pdf'),
(18, 'http://localhost/Sites/Server/AndroidImageUpload/uploads/stackoverflow.pdf', 'stackoverflow.pdf');

-- --------------------------------------------------------

--
-- Table structure for table `State_Calc`
--

CREATE TABLE `State_Calc` (
  `timestamp` datetime NOT NULL,
  `roll` varchar(50) NOT NULL,
  `involvement` int(11) NOT NULL,
  `Arousal` int(11) NOT NULL,
  `Valence` int(11) NOT NULL,
  `Arousal_Valence_state` int(11) NOT NULL,
  `Classroom_Activity_State` int(11) NOT NULL,
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
  `Timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `AlertType` int(11) NOT NULL,
  `Alert_count_not_engaged` int(11) NOT NULL,
  `Alert_count_not_understanding` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `temp_states`
--

CREATE TABLE `temp_states` (
  `student_id` varchar(10) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `Involvement` int(11) NOT NULL,
  `Activity` int(11) NOT NULL,
  `Arousal` int(11) NOT NULL,
  `Valence` int(11) NOT NULL,
  `State` int(11) NOT NULL,
  `Mental_State` int(11) NOT NULL,
  `Visualization_State` int(11) NOT NULL,
  `Alert` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `temp_states`
--

INSERT INTO `temp_states` (`student_id`, `timestamp`, `Involvement`, `Activity`, `Arousal`, `Valence`, `State`, `Mental_State`, `Visualization_State`, `Alert`) VALUES
('001', '2020-03-14 13:45:20', 1, 1, 1, 1, 1, 1, 2, 0),
('160101071', '2020-03-14 13:46:20', 1, 1, 1, 1, 1, 1, 3, 1),
('asus', '2020-04-24 18:36:53', 1, 1, 1, 1, 1, 1, 3, 1),
('htc', '2020-04-24 18:37:04', 1, 1, 1, 1, 1, 1, 3, 1),
('moto', '2020-04-24 18:37:25', 1, 1, 1, 1, 1, 1, 3, 1),
('sony1', '2020-04-24 18:37:34', 1, 1, 1, 1, 1, 1, 3, 1),
('sony2', '2020-04-24 18:37:42', 1, 1, 1, 1, 1, 1, 3, 1),
('samsung', '2020-04-24 18:38:03', 1, 1, 1, 1, 1, 1, 3, 1),
('160101072', '2020-04-24 19:57:36', 1, 1, 1, 1, 1, 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `Visualisation_States`
--

CREATE TABLE `Visualisation_States` (
  `timestamp` datetime NOT NULL,
  `roll` text NOT NULL,
  `Derived_States` int(11) NOT NULL,
  `Alert` int(11) NOT NULL
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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT for table `queries`
--
ALTER TABLE `queries`
  MODIFY `q_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `slides`
--
ALTER TABLE `slides`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
