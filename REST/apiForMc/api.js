const express = require('express')
const path = require('path')
const fs = require('fs')
const http = require('http')
const https = require('https')
const resourcesPath = path.join(__dirname, './data/')
const esJson = require('./data/es.json')
const arenaJson = require('./data/arena.json')
const server = express()
const app = {
    port: 3000,
    ssl: {
      key: fs.readFileSync('/etc/letsencrypt/live/play.divecrafts.net/privkey.pem', 'utf8'),
      cert: fs.readFileSync('/etc/letsencrypt/live/play.divecrafts.net/cert.pem', 'utf8')
    }
}


/** FUNCTIONAL **/

const get = (id, callback, isApi = true) => !isApi ? server.get(id, callback) : server.get(`/api/${id}`, callback)
const post = (id, callback) => server.post(`/api/${id}`, callback)
const readResource = file => JSON.parse(fs.readFileSync(file, 'utf8'))

//TODO: Make a ratelimit
server.use(express.urlencoded())
server.use(express.json())

fs.readdir(resourcesPath, (err, files) => files.map(
  file =>
    get(file, (req, res) => file.includes('json') ?
      res.send(readResource(`${resourcesPath}/${file}`)) :
      res.sendFile(`${resourcesPath}/${file}`)
    )
))

const hook = (port) => console.log(`Backend is up listening the port ${port} 🎨`)

https.createServer(app.ssl, server).listen(3003, hook(3003))
http.createServer(server).listen(app.port, hook(app.port))
