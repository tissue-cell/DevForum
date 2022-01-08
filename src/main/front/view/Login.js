import React, {Component} from 'react';

class Login extends Component{

  constructor(props) {
      super(props);
      this.state = {
          id: "",
          password: ""
      };
  };
  handleChange = (e) => {
      this.setState({
          [e.target.name]: e.target.value
      });
  };
  handleOnClick = (e) => {
      console.log(this.state.id, this.state.password);
      this.login()
  }
  login = () => {
    fetch('/api/login',{method: 'POST',
    headers : {
      'X-XSRF-TOKEN': this.state.csrfToken,
      'content-type':'application/json',
    },
    body: JSON.stringify({
            id: this.state.id,
            password: this.state.password
      })
    })
    .then(response => response.text())
    .then(message => {
      this.setState({message: message});
      console.log("response : "+this.state.message);
    });
  }

  render(){
    return (
      <div className="App">

        <form className="LoginForm">
          <h2 className="LoginHeader"> Login페이지 </h2>
          <input type="text" id="inputId" className="form-control" placeholder="ID" name="id" onChange={this.handleChange}/>
          <input type="password" id="inputPassword" className="form-control" placeholder="PASSWORD" name="password" onChange={this.handleChange}/>
          <button className="btn btn-lg btn-primary btn-block" type="button" onClick={this.handleOnClick}> 로그인 </button>
        </form>
      </div>
    );
  }
}
export default Login;
