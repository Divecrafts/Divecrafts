# Removes the oldest backup
files=$(ls -1 | wc -l)

if [ $files -gt 3 ] || [ $files -eq 3 ]; then
    rm "$(ls -t | tail -1)"
fi

# Gen the name of the backup
name=home$(date +%d%m%Y)

# Gen a temp file for compress
echo "Creating a backup file"
rsync -raz /home /backups/
mv home $name

# Compress the temp file
tar -zcvf $name.tgz $name

# Clear temp file
rm -rf $name

echo "Backup created!"
echo "Script by clonalejandro"
