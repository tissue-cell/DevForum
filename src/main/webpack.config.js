const HtmlWebpackPlugin = require('html-webpack-plugin')
const path = require('path');

module.exports = {
    context: path.resolve(__dirname, 'front'),
    entry:{
        index: './index.js',
    },
    output:{
        path: path.resolve(__dirname, 'webapp/resources'),
        filename: '[name].js',
        publicPath: '/resources/',
    },
    module:{
        rules:[
            {
                test: /\.js$/,
                exclude: /node_moules/,
                use: 'babel-loader',
            },
            {
              test:/\.css$/,
              use:['style-loader','css-loader'],
            },
            {
              test: /\.(png|svg)$/,
              use: [
                {
                  loader: 'file-loader',
                  options: {
                    name: 'images/[name].[ext]?[hash]',
                  }
                },
              ],
            },
        ],
      },
      /*plugins: [new HtmlWebpackPlugin()],*/     //html 자동생성
      
      mode: 'production',
};