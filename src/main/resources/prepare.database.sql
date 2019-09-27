-- SQL Dump

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

-- --------------------------------------------------------

--
-- Primary key for table `ip2location_db11`
--
ALTER TABLE `ip2location_db11`
  ADD `TABLE_ID` BIGINT NOT NULL AUTO_INCREMENT FIRST,
  ADD PRIMARY KEY (`TABLE_ID`);

-- --------------------------------------------------------

--
-- Primary key for table `ip2location_db11_ipv6`
--
ALTER TABLE `ip2location_db11_ipv6`
  ADD `TABLE_ID` BIGINT NOT NULL AUTO_INCREMENT FIRST,
  ADD PRIMARY KEY (`TABLE_ID`);

-- --------------------------------------------------------

--
-- Table structure for table `location`
--

CREATE TABLE `location` (
  `TABLE_ID` bigint(20) NOT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `country_name` varchar(255) DEFAULT NULL,
  `city_name` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `updated` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `location_place_connection`
--

CREATE TABLE `location_place_connection` (
  `locationID` bigint(20) DEFAULT NULL,
  `placeID` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `place`
--

CREATE TABLE `place` (
  `TABLE_ID` bigint(20) NOT NULL,
  `place_id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `rating` double DEFAULT NULL,
  `address` text,
  `phone` varchar(255) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  `map_url` varchar(255) DEFAULT NULL,
  `photo_reference` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `updated` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `location`
--
ALTER TABLE `location`
  ADD PRIMARY KEY (`TABLE_ID`),
  ADD UNIQUE KEY `path` (`path`),
  ADD KEY `latitude` (`latitude`),
  ADD KEY `longitude` (`longitude`),
  ADD KEY `latitude_2` (`latitude`,`longitude`),
  ADD KEY `updated` (`updated`);

--
-- Indexes for table `location_place_connection`
--
ALTER TABLE `location_place_connection`
  ADD KEY `coordinateID` (`locationID`),
  ADD KEY `placeID` (`placeID`);

--
-- Indexes for table `place`
--
ALTER TABLE `place`
  ADD PRIMARY KEY (`TABLE_ID`),
  ADD KEY `place_id` (`place_id`),
  ADD KEY `rating` (`rating`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `location`
--
ALTER TABLE `location`
  MODIFY `TABLE_ID` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `place`
--
ALTER TABLE `place`
  MODIFY `TABLE_ID` bigint(20) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
