import React, {Component} from 'react';
import { BrowserRouter,Route,Routes,Link, } from 'react-router-dom';
import Index from './view/Index';
import Login from './view/Login';


class App extends Component{


  render(){
    return (
      <div>
        <BrowserRouter>
        <ul>
          <li>
            <Link to ="/">Home</Link>
          </li>
          <li>
            <Link to ="/login">Login</Link>
          </li>
        </ul>
          <Routes>
            <Route path="/" element={<Index />}/>
            <Route path="/login" element={<Login />}/>
            <Route path="/*" element={<Index />}/>
          </Routes>
        </BrowserRouter>
      </div>
    );
  }
}

export default App;
