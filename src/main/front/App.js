import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component{

  state = {};

  componentDidMount(){
    setInterval(this.hello, 250);
  }

  hello = () => {
    fetch('/api/hello',{method: 'POST'})
      .then(response => response.text())
      .then(message => {
        this.setState({message: message});
      });
  }
  render(){
    return (
      <div className="App">
          <header className="App-header">
              <img src={logo} className="App-logo" alt="logo"/>
              <h1 className="App-title">{this.state.message}</h1>
          </header>
          <a></a>
      </div>
  );
  }
}

export default App;
