'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {clientes: []};
	}

	componentDidMount() {
		client({method: 'GET', path: '/api/clientes'}).done(response => {
			this.setState({clientes: response.entity});
		});
	}

	render() {
		return (
			<ClienteList clientes={this.state.clientes}/>
		)
	}
}

class ClienteList extends React.Component{
	render() {
	    const clientes = this.props.clientes.map(cliente =>
        	<Cliente key={cliente.id} cliente={cliente}/>
        );
        return (
            <table>
        	    <tbody>
        		    <tr>
        			    <th>Id</th>
        				<th>Limite</th>
        				<th>Saldo</th>
        			</tr>
        			{clientes}
        		</tbody>
        	</table>
        )
	}
}

class Cliente extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.cliente.id}</td>
				<td>{this.props.cliente.limite}</td>
				<td>{this.props.cliente.saldo}</td>
			</tr>
		)
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)