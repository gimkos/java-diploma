-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1
-- Время создания: Апр 21 2022 г., 12:22
-- Версия сервера: 10.4.17-MariaDB
-- Версия PHP: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `diploma_scores`
--

-- --------------------------------------------------------

--
-- Структура таблицы `tb_game`
--

CREATE TABLE `tb_game` (
  `gameID` int(11) NOT NULL,
  `gameName` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `tb_game`
--

INSERT INTO `tb_game` (`gameID`, `gameName`) VALUES
(1, 'Snake'),
(2, 'BreakOut'),
(3, 'FlappyBird');

-- --------------------------------------------------------

--
-- Структура таблицы `tb_scores`
--

CREATE TABLE `tb_scores` (
  `userID` varchar(16) NOT NULL,
  `gameID` int(11) NOT NULL,
  `score` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `tb_scores`
--

INSERT INTO `tb_scores` (`userID`, `gameID`, `score`) VALUES
('14k0BdFxuBdXu1jj', 1, 0),
('14k0BdFxuBdXu1jj', 2, 0),
('14k0BdFxuBdXu1jj', 3, 0),
('0rUYV85H1CwXV02M', 1, 230),
('0rUYV85H1CwXV02M', 2, 4),
('0rUYV85H1CwXV02M', 3, 7),
('a9GtB1WJbcbjJs8B', 1, 130),
('a9GtB1WJbcbjJs8B', 2, 68),
('a9GtB1WJbcbjJs8B', 3, 0),
('18x9V6659BiP7NQp', 1, 0),
('18x9V6659BiP7NQp', 2, 0),
('18x9V6659BiP7NQp', 3, 0),
('R4bb53J9Fe7ecRFb', 1, 0),
('R4bb53J9Fe7ecRFb', 2, 0),
('R4bb53J9Fe7ecRFb', 3, 0);

-- --------------------------------------------------------

--
-- Структура таблицы `tb_users`
--

CREATE TABLE `tb_users` (
  `userID` varchar(16) NOT NULL,
  `userName` varchar(16) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `tb_users`
--

INSERT INTO `tb_users` (`userID`, `userName`) VALUES
('0rUYV85H1CwXV02M', 'Test2'),
('14k0BdFxuBdXu1jj', 'Test1'),
('18x9V6659BiP7NQp', 'FontTest'),
('a9GtB1WJbcbjJs8B', 'Test3'),
('R4bb53J9Fe7ecRFb', 'Test4');

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `tb_game`
--
ALTER TABLE `tb_game`
  ADD PRIMARY KEY (`gameID`);

--
-- Индексы таблицы `tb_scores`
--
ALTER TABLE `tb_scores`
  ADD KEY `userID` (`userID`),
  ADD KEY `gameID` (`gameID`);

--
-- Индексы таблицы `tb_users`
--
ALTER TABLE `tb_users`
  ADD PRIMARY KEY (`userID`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `tb_game`
--
ALTER TABLE `tb_game`
  MODIFY `gameID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `tb_scores`
--
ALTER TABLE `tb_scores`
  ADD CONSTRAINT `tb_scores_ibfk_1` FOREIGN KEY (`gameID`) REFERENCES `tb_game` (`gameID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tb_scores_ibfk_2` FOREIGN KEY (`userID`) REFERENCES `tb_users` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
