'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

const root = '/api';

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

	onCreate(newCliente) {
    	client({
    		method: 'POST',
    		path: '/api/clientes',
    		entity: newCliente,
    		headers: {'Content-Type': 'application/json'}
    	})
    }

	render() {
		return (
		    <div>
			    <CreateDialog attributes={this.state.attributes} onCreate={this.onCreate}/>
			    <ClienteList clientes={this.state.clientes}/>
			</div>
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

class CreateDialog extends React.Component {

	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleSubmit(e) {
		e.preventDefault();
		const newCliente = {};
       	newCliente['limite'] = ReactDOM.findDOMNode(this.refs['limite']).value.trim();
       	newCliente['saldo'] = ReactDOM.findDOMNode(this.refs['saldo']).value.trim();
		this.props.onCreate(newCliente);
		window.location = "#";
	}

	render() {
		return (
			<div>
				<a href="#createCliente">Create</a>

				<div id="createCliente" className="modalDialog">
					<div>
						<a href="#" title="Close" className="close">X</a>

						<h2>Create new cliente</h2>

						<form>
							<p key="limite">
                            	<input type="text" placeholder="limite" ref="limite" className="field"/>
                            </p>
							<p key="saldo">
                            	<input type="text" placeholder="saldo" ref="saldo" className="field"/>
                            </p>
							<button onClick={this.handleSubmit}>Create</button>
						</form>
					</div>
				</div>
			</div>
		)
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)