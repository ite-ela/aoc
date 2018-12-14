use std::io::prelude::*;
use std::io::BufReader;
use std::fs::File;
use std::io::Lines;
use std::path::Path;
use std::error::Error;


pub struct FileReader {
    path: String,
}

impl FileReader {
    pub fn new(path: &str) -> FileReader {
        FileReader {
            path: path.to_string(),
        }
    }

    pub fn lines_iterator(&self) -> Lines<BufReader<File>> {
        let path = Path::new(&self.path);
        let display = path.display();

        let f = match File::open(path) {
            Err(why) => panic!("couldn't open {}: {}", display,
                               why.description()),
            Ok(file) => file,
        };
        let reader = BufReader::new(f);
        return reader.lines();
    }
}
