package org.strasa.middleware.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.StudyVariableMapper;
import org.strasa.middleware.model.StudyVariable;
import org.strasa.middleware.model.StudyVariableExample;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class StudyVariableManagerImpl {

	@WireVariable
	ConnectionFactory connectionFactory;

	private StudyVariableMapper getMapper(SqlSession session) {

		return session.getMapper(StudyVariableMapper.class);
	}

	public StudyVariableManagerImpl() {

	}

	public boolean hasVariable(String variable) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();

		try {
			StudyVariableExample query = new StudyVariableExample();

			query.createCriteria().andVariablecodeLikeInsensitive(variable);
			if (getMapper(session).selectByExample(query).isEmpty())
				return false;

		} finally {
			session.close();
		}

		return true;
	}

	public ArrayList<String> hasVariableInArray(List<String> list) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();

		ArrayList<String> returnVal = new ArrayList<String>();
		try {
			StudyVariableExample query = new StudyVariableExample();
			for (String variable : list) {
				query.createCriteria().andVariablecodeLikeInsensitive(variable);
				if (getMapper(session).selectByExample(query).isEmpty())
					returnVal.add(variable);
			}

		} finally {
			session.close();
		}

		return returnVal;
	}

	public ArrayList<String> correctVariableCase(List<String> lstVar) {
		ArrayList<String> returnVal = new ArrayList<String>();
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();

		try {
			StudyVariableExample query = new StudyVariableExample();
			for (String variable : lstVar) {
				query.createCriteria().andVariablecodeLikeInsensitive(variable);
				if (!getMapper(session).selectByExample(query).isEmpty())
					returnVal.add(getMapper(session).selectByExample(query).get(0).getVariablecode());
				else
					returnVal.add(variable);
				query.clear();

			}

		} finally {
			session.close();
		}

		return returnVal;
	}

	public List<StudyVariable> getVariables() {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();

		try {
			return getMapper(session).selectByExample(null);
		} finally {
			session.close();
		}

	}

	public List<StudyVariable> getVariables(List<StudyVariable> filter) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();

		try {
			ArrayList<Integer> lstFilterIds = new ArrayList<Integer>();
			for (StudyVariable var : filter) {
				lstFilterIds.add(var.getId());
			}
			StudyVariableExample example = new StudyVariableExample();
			example.createCriteria().andIdNotIn(lstFilterIds);
			return getMapper(session).selectByExample(example);
		} finally {
			session.close();
		}

	}

	public List<StudyVariable> getVariables(String sort) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();

		try {
			StudyVariableExample example = new StudyVariableExample();
			example.createCriteria().andVariablecodeLike(sort + "%");
			return getMapper(session).selectByExample(example);
		} finally {
			session.close();
		}

	}

	public List<StudyVariable> getVariablesLike(String sort) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();

		try {
			StudyVariableExample example = new StudyVariableExample();
			example.createCriteria().andVariablecodeLike(sort + "%");
			return getMapper(session).selectByExample(example);
		} finally {
			session.close();
		}

	}

	public StudyVariable getVariableByName(String var) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();

		try {
			StudyVariableExample query = new StudyVariableExample();
			query.createCriteria().andApplytofilterEqualTo(var);
			return getMapper(session).selectByExample(query).get(0);
		} finally {
			session.close();
		}
	}

	public StudyVariable getVariableInfoByName(String var) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();

		try {
			StudyVariableExample query = new StudyVariableExample();
			query.createCriteria().andVariablecodeEqualTo(var);
			return getMapper(session).selectByExample(query).get(0);
		} finally {
			session.close();
		}
	}

	private String buildRelevanceClause(String var) {
		return "CASE WHEN variablecode like '" + var + " %' THEN 0  WHEN variablecode like '" + var + "%' THEN 1 WHEN variablecode like '% " + var + "%' THEN 2 ELSE 3  END, variablecode";
	}

	public void deleteById(Integer id) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();

		// TODO Auto-generated method stub
		try {
			getMapper(session).deleteByPrimaryKey(id);

			session.commit();

		} finally {
			session.close();
		}

	}

	public HashMap<String, StudyVariable> getConditionVariable(ArrayList<String> varNames) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();

		try {
			StudyVariableExample example = new StudyVariableExample();
			example.createCriteria().andCategoryEqualTo("CONDITION").andVariablecodeIn(varNames);

			List<StudyVariable> lstVar = getMapper(session).selectByExample(example);
			HashMap<String, StudyVariable> returnVal = new HashMap<String, StudyVariable>();
			for (StudyVariable var : lstVar) {
				returnVal.put(var.getVariablecode(), var);
			}

			return returnVal;
		} finally {
			session.close();
		}
	}

	public List<StudyVariable> getFactorVariable(List<String> list) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();

		try {
			StudyVariableExample example = new StudyVariableExample();
			example.createCriteria().andCategoryEqualTo("FACTOR").andVariablecodeIn(list);

			return getMapper(session).selectByExample(example);
		} finally {

			session.close();
		}
	}

	public List<StudyVariable> getVariateVariable(List<String> asList) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();

		// TODO Auto-generated method stub

		try {
			StudyVariableExample example = new StudyVariableExample();
			example.createCriteria().andCategoryEqualTo("VARIATE").andVariablecodeIn(asList);

			return getMapper(session).selectByExample(example);
		} finally {

			session.close();
		}
	}
}
