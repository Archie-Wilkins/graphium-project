-- ------------------------------------
-- Database 'graphium'
-- ------------------------------------
use `graphium`;

SET FOREIGN_KEY_CHECKS = 0;
truncate table `users`;
truncate table `authorities`;
truncate table `documents`;
SET FOREIGN_KEY_CHECKS = 1;

-- ------------------------------------
-- Users
-- ------------------------------------

-- both the passwords decrypt to just be password (using bcrypt)
INSERT INTO `users` (`username`,`password`, `enabled`) VALUES ('user','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1);
INSERT INTO `users` (`username`,`password`,`enabled`) VALUES ('admin','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1);

INSERT INTO `authorities`(`username`,`authority`) VALUES ("user","researcher");
INSERT INTO `authorities`(`username`,`authority`) VALUES ("admin","admin");


-- ------------------------------------
-- Documents
-- ------------------------------------
# INSERT INTO `documents` (`title`, `type_pdf`) VALUES ('Test 1', 2);
# INSERT INTO `documents` (`title`, `type_pdf`) VALUES ('Test 2', 2);
# INSERT INTO `documents` (`title`, `type_pdf`) VALUES ('Test 3', 2);
# INSERT INTO `documents` (`title`, `type_pdf`) VALUES ('Test 4', 2);
