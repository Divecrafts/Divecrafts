const express = require('express')
const path = require('path')
const fs = require('fs')
const resourcesPath = path.join(__dirname, './data/')
const esJson = require('./data/es.json')
const arenaJson = require('./data/arena.json')
const server = express()
const app = {
    port: 3000
}


/** FUNCTIONAL **/

const get = (id, callback, isApi = true) => !isApi ? server.get(id, callback) : server.get(`/api/${id}`, callback)
const post = (id, callback) => server.post(`/api/${id}`, callback)
const readResource = file => JSON.parse(fs.readFileSync(`${resourcesPath}/${file}`, 'utf8'))

//TODO: Make a ratelimit
server.use(express.urlencoded())
server.use(express.json())

fs.readdir(resourcesPath, (err, files) => files.map(
  file =>
    get(file, (req, res) =>
      res.send(readResource(file))
    )
))

server.listen(app.port, () => console.log(`Backend is up listening the port ${app.port} 🎨`))
