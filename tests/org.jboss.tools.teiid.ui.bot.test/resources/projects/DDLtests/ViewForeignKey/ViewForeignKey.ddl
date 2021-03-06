
CREATE VIEW helpTable (
	Column_1 string(4000) NOT NULL,
	Column_2 string(4000),
	CONSTRAINT PrimaryKey PRIMARY KEY(Column_1) OPTIONS(NAMEINSOURCE 'PrimaryKeySource'),
	CONSTRAINT UniqueConstraint UNIQUE(Column_2) OPTIONS(NAMEINSOURCE 'UniqueConstraintSource')
) OPTIONS(NAMEINSOURCE 'helpTableSource', UPDATABLE 'TRUE') 
AS
	SELECT
		'test' AS Column_1, 'test2' AS Column_2;

CREATE VIEW MyTable (
	fk_Column1 string(4000),
	fk_Column2 string(4000),
	CONSTRAINT ForeignKey1 FOREIGN KEY(fk_Column1) REFERENCES helpTable(Column_1) OPTIONS(NAMEINSOURCE 'ForeignKey1Source'),
	CONSTRAINT ForeignKey2 FOREIGN KEY(fk_Column2) REFERENCES helpTable(Column_2) OPTIONS(NAMEINSOURCE 'ForeignKey2Source')
) OPTIONS(NAMEINSOURCE 'MyTableSource', UPDATABLE 'TRUE') 
AS
	SELECT
		'test' AS fk_Column1, 'test2' AS fk_Column2;

